package cn.pridezh.rbac.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.pridezh.rbac.convert.SysUserConvert;
import cn.pridezh.rbac.domain.dto.LoginDTO;
import cn.pridezh.rbac.domain.po.SysPermission;
import cn.pridezh.rbac.domain.po.SysRolePermission;
import cn.pridezh.rbac.domain.po.SysUser;
import cn.pridezh.rbac.domain.po.SysUserRole;
import cn.pridezh.rbac.domain.vo.user.SysUserInfoVO;
import cn.pridezh.rbac.domain.vo.TokenVO;
import cn.pridezh.rbac.exception.ServiceException;
import cn.pridezh.rbac.mapper.SysPermissionMapper;
import cn.pridezh.rbac.mapper.SysRolePermissionMapper;
import cn.pridezh.rbac.mapper.SysUserMapper;
import cn.pridezh.rbac.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 20:11
 */
@AllArgsConstructor
@Service
public class AuthService {

    private SysUserMapper sysUserMapper;
    private SysPermissionMapper sysPermissionMapper;
    private SysUserRoleMapper sysUserRoleMapper;
    private SysRolePermissionMapper sysRolePermissionMapper;

    private SysUserConvert sysUserConvert;

    public TokenVO login(LoginDTO loginDTO) {
        SysUser sysAdmin = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, loginDTO.getUsername()));
        if (sysAdmin == null) {
            throw new ServiceException(1001, "用户不存在");
        }

        if (!sysAdmin.getPassword().equals(SaSecureUtil.sha256(loginDTO.getPassword()))) {
            throw new ServiceException(1002, "密码错误");
        }

        StpUtil.login(sysAdmin.getId());

        TokenVO tokenVO = new TokenVO();
        tokenVO.setName(StpUtil.getTokenName());
        tokenVO.setValue(StpUtil.getTokenValue());
        return tokenVO;
    }

    public SysUserInfoVO info() {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserMapper.selectById(userId);
        SysUserInfoVO sysUserInfoVO = sysUserConvert.toVO(sysUser);

        sysUserInfoVO.setPermissions(access());

        return sysUserInfoVO;
    }

    /**
     * 获取当前用户所有权限Code
     */
    public List<String> access() {
        Long userId = StpUtil.getLoginIdAsLong();

        // 超级管理员获取全部权限
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser.getSuperAdmin()) {
            return sysPermissionMapper.selectList(new LambdaQueryWrapper<SysPermission>()
                            .select(SysPermission::getCode))
                    .stream().map(SysPermission::getCode).toList();
        }

        // 获取该用户的所有角色ID
        List<Long> roleIds = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                        .select(SysUserRole::getRoleId)
                        .eq(SysUserRole::getUserId, userId))
                .stream().map(SysUserRole::getRoleId).toList();

        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        // 获取该用户的所有权限ID
        // selectBatchIds的底层实现也是in 但指定查询查询减少网络传输
        List<Long> permissionIds = sysRolePermissionMapper.selectList(new LambdaQueryWrapper<SysRolePermission>()
                        .select(SysRolePermission::getPermissionId)
                        .in(SysRolePermission::getRoleId, roleIds))
                .stream().map(SysRolePermission::getPermissionId).toList();

        if (CollUtil.isEmpty(permissionIds)) {
            return Collections.emptyList();
        }

        // 获取所有权限Code集合
        return sysPermissionMapper.selectList(new LambdaQueryWrapper<SysPermission>()
                        .select(SysPermission::getCode)
                        .in(SysPermission::getId, permissionIds))
                .stream().map(SysPermission::getCode).toList();
    }

}
