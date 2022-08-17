package cn.pridezh.rbac.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.pridezh.rbac.convert.SysRoleConvert;
import cn.pridezh.rbac.convert.SysUserConvert;
import cn.pridezh.rbac.domain.dto.PageDTO;
import cn.pridezh.rbac.domain.dto.SysUserCreateDTO;
import cn.pridezh.rbac.domain.dto.SysUserUpdateDTO;
import cn.pridezh.rbac.domain.po.*;
import cn.pridezh.rbac.domain.vo.user.SysUserItemVO;
import cn.pridezh.rbac.exception.ServiceException;
import cn.pridezh.rbac.mapper.SysRoleMapper;
import cn.pridezh.rbac.mapper.SysUserMapper;
import cn.pridezh.rbac.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author PrideZH
 * @since 2022-08-03
 */
@AllArgsConstructor
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    private SysUserMapper sysUserMapper;
    private SysRoleMapper sysRoleMapper;
    private SysUserRoleMapper sysUserRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    public void create(SysUserCreateDTO sysUserCreateDTO) {
        if (sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, sysUserCreateDTO.getUsername())) != null) {
            throw new ServiceException(1001, "用户名已存在");
        }
        SysUser sysUser = SysUserConvert.INSTANCE.toPO(sysUserCreateDTO);
        sysUser.setPassword(SaSecureUtil.sha256(sysUser.getPassword()));
        sysUserMapper.insert(sysUser);

        if (CollUtil.isNotEmpty(sysUserCreateDTO.getRoleIds())) {
            sysUserCreateDTO.getRoleIds().forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            });
        }
    }

    public IPage<SysUserItemVO> page(PageDTO pageDTO) {
        Page<SysUser> page = sysUserMapper.selectPage(new Page<>(pageDTO.getPage(), pageDTO.getSize()), null);

        return page.convert(sysUser -> {
            SysUserItemVO sysUserItemVO = SysUserConvert.INSTANCE.toItemVO(sysUser);

            // 设置用户拥有的角色
            List<Long> roleIds = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                            .select(SysUserRole::getRoleId)
                            .eq(SysUserRole::getUserId, sysUserItemVO.getId()))
                    .stream().map(SysUserRole::getRoleId).toList();

            if (CollUtil.isEmpty(roleIds)) {
                sysUserItemVO.setRoles(Collections.emptyList());
                return sysUserItemVO;
            } else {
                List<SysRole> sysRoles = sysRoleMapper.selectBatchIds(roleIds);
                sysUserItemVO.setRoles(SysRoleConvert.INSTANCE.toVOList(sysRoles));
            }

            return sysUserItemVO;
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserUpdateDTO sysUserUpdateDTO) {
        SysUser sysUser = sysUserMapper.selectById(sysUserUpdateDTO.getId());
        if (sysUser.getSuperAdmin()) {
            throw new ServiceException(1001, "不能修改超级管理员");
        }

        sysUserMapper.updateById(SysUserConvert.INSTANCE.toPO(sysUserUpdateDTO));

        // 修改用户的角色
        if (CollUtil.isNotEmpty(sysUserUpdateDTO.getRoleIds())) {
            // 删除旧角色
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, sysUserUpdateDTO.getId()));
            // 添加新角色
            sysUserUpdateDTO.getRoleIds().forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUserUpdateDTO.getId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        Long userId = StpUtil.getLoginIdAsLong();
        if (userId.equals(id)) {
            throw new ServiceException(1001, "不能删除当前用户");
        }

        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser.getSuperAdmin()) {
            throw new ServiceException(1002, "不能删除超级管理员");
        }

        sysUserMapper.deleteById(id);
        sysUserRoleMapper.deleteById(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
    }

}
