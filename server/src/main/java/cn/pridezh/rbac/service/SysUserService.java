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
import cn.pridezh.rbac.manager.MinioManager;
import cn.pridezh.rbac.mapper.SysRoleMapper;
import cn.pridezh.rbac.mapper.SysUserMapper;
import cn.pridezh.rbac.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

/**
 * @author PrideZH
 * @since 2022-08-03
 */
@AllArgsConstructor
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    private static final List<String> ALLOW_IMAGE_TYPE = List.of("image/jpeg", "image/png");
    
    private Environment environment;

    private SysUserMapper sysUserMapper;
    private SysRoleMapper sysRoleMapper;
    private SysUserRoleMapper sysUserRoleMapper;

    private MinioManager minioManager;

    private SysUserConvert sysUserConvert;
    private SysRoleConvert sysRoleConvert;

    @Transactional(rollbackFor = Exception.class)
    public void create(SysUserCreateDTO sysUserCreateDTO) {
        if (sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, sysUserCreateDTO.getUsername())) != null) {
            throw new ServiceException(1001, "用户名已存在");
        }
        SysUser sysUser = sysUserConvert.toPO(sysUserCreateDTO);
        sysUser.setPassword(SaSecureUtil.sha256(sysUser.getPassword()));
        sysUserMapper.insert(sysUser);

        if (CollUtil.isNotEmpty(sysUserCreateDTO.getRoleIds())) {
            List<SysUserRole> sysUserRoles = sysUserCreateDTO.getRoleIds().stream()
                    .map(roleId -> new SysUserRole()
                            .setUserId(sysUser.getId())
                            .setRoleId(roleId))
                    .toList();
            sysUserRoleMapper.insertBatchSomeColumn(sysUserRoles);
        }
    }

    public IPage<SysUserItemVO> page(PageDTO pageDTO) {
        Page<SysUser> page = sysUserMapper.selectPage(new Page<>(pageDTO.getPage(), pageDTO.getSize()), null);

        return page.convert(sysUser -> {
            SysUserItemVO sysUserItemVO = sysUserConvert.toItemVO(sysUser);

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
                sysUserItemVO.setRoles(sysRoleConvert.toVOList(sysRoles));
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

        sysUserMapper.updateById(sysUserConvert.toPO(sysUserUpdateDTO));

        // 修改用户的角色
        if (CollUtil.isNotEmpty(sysUserUpdateDTO.getRoleIds())) {
            // 删除旧角色
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, sysUserUpdateDTO.getId()));
            // 添加新角色
            List<SysUserRole> sysUserRoles = sysUserUpdateDTO.getRoleIds().stream()
                    .map(roleId -> new SysUserRole()
                            .setUserId(sysUserUpdateDTO.getId())
                            .setRoleId(roleId))
                    .toList();
            sysUserRoleMapper.insertBatchSomeColumn(sysUserRoles);
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

    public String updateAvatar(MultipartFile file) throws Exception {
        Long userId = StpUtil.getLoginIdAsLong();
        SysUser sysUser = sysUserMapper.selectById(userId);

        if (sysUser.getSuperAdmin()) {
            throw new ServiceException(1001, "不能修改超级管理员头像");
        }

        if (!ALLOW_IMAGE_TYPE.contains(file.getContentType())) {
            throw new ServiceException(1002, "图片类型错误");
        }
        if (file.getSize() >= 500 * 1024) {
            throw new ServiceException(1003, "图片大小超过500KB");
        }

        String avatarUrl = minioManager.putObject(file);

        sysUserMapper.updateById(sysUser.setAvatarUrl(avatarUrl));

        return environment.getProperty("minio.url") + "/" + environment.getProperty("minio.bucket") + "/" + avatarUrl;
    }
}
