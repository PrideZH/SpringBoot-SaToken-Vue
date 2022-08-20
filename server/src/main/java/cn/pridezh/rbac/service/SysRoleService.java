package cn.pridezh.rbac.service;

import cn.hutool.core.collection.CollUtil;
import cn.pridezh.rbac.convert.SysPermissionConvert;
import cn.pridezh.rbac.convert.SysRoleConvert;
import cn.pridezh.rbac.domain.dto.PageDTO;
import cn.pridezh.rbac.domain.dto.SysRoleCreateDTO;
import cn.pridezh.rbac.domain.dto.SysRoleUpdateDTO;
import cn.pridezh.rbac.domain.po.SysPermission;
import cn.pridezh.rbac.domain.po.SysRole;
import cn.pridezh.rbac.domain.po.SysRolePermission;
import cn.pridezh.rbac.domain.po.SysUserRole;
import cn.pridezh.rbac.domain.vo.role.SysRoleItemVO;
import cn.pridezh.rbac.exception.ServiceException;
import cn.pridezh.rbac.mapper.SysPermissionMapper;
import cn.pridezh.rbac.mapper.SysRoleMapper;
import cn.pridezh.rbac.mapper.SysRolePermissionMapper;
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
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

    private SysRoleMapper sysRoleMapper;
    private SysUserRoleMapper sysUserRoleMapper;
    private SysPermissionMapper sysPermissionMapper;
    private SysRolePermissionMapper sysRolePermissionMapper;

    private SysRoleConvert sysRoleConvert;
    private SysPermissionConvert sysPermissionConvert;

    @Transactional(rollbackFor = Exception.class)
    public void create(SysRoleCreateDTO sysRoleCreateDTO) {
        if (sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getName, sysRoleCreateDTO.getName())) != null) {
            throw new ServiceException(1001, "角色已存在");
        }
        SysRole sysRole = sysRoleConvert.toPO(sysRoleCreateDTO);
        sysRoleMapper.insert(sysRole);

        if (CollUtil.isNotEmpty(sysRoleCreateDTO.getPermissionIds())) {
            List<SysRolePermission> sysRolePermissions = sysRoleCreateDTO.getPermissionIds().stream()
                    .map(permissionId -> new SysRolePermission()
                            .setRoleId(sysRole.getId())
                            .setPermissionId(permissionId))
                    .toList();
            sysRolePermissionMapper.insertBatchSomeColumn(sysRolePermissions);
        }
    }

    public IPage<SysRoleItemVO> page(PageDTO pageDTO) {
        Page<SysRole> page = sysRoleMapper.selectPage(new Page<>(pageDTO.getPage(), pageDTO.getSize()), null);

        return page.convert(sysRole -> {
            SysRoleItemVO sysRoleItemVO = sysRoleConvert.toItemVO(sysRole);

            // 设置角色拥有的权限
            List<Long> permissionIds = sysRolePermissionMapper.selectList(new LambdaQueryWrapper<SysRolePermission>()
                            .select(SysRolePermission::getPermissionId)
                            .eq(SysRolePermission::getRoleId, sysRoleItemVO.getId()))
                    .stream().map(SysRolePermission::getPermissionId).toList();

            if (CollUtil.isEmpty(permissionIds)) {
                sysRoleItemVO.setPermissions(Collections.emptyList());
            } else {
                List<SysPermission> sysPermissions = sysPermissionMapper.selectBatchIds(permissionIds);
                sysRoleItemVO.setPermissions(sysPermissionConvert.toVOList(sysPermissions));
            }

            return sysRoleItemVO;
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleUpdateDTO sysRoleUpdateDTO) {
        sysRoleMapper.updateById(sysRoleConvert.toPO(sysRoleUpdateDTO));

        // 修改角色的权限
        if (CollUtil.isNotEmpty(sysRoleUpdateDTO.getPermissionIds())) {
            // 删除旧权限
            sysRolePermissionMapper.delete(new LambdaQueryWrapper<SysRolePermission>()
                    .eq(SysRolePermission::getRoleId, sysRoleUpdateDTO.getId()));
            // 添加新权限
            List<SysRolePermission> sysRolePermissions = sysRoleUpdateDTO.getPermissionIds().stream()
                    .map(permissionId -> new SysRolePermission()
                            .setRoleId(sysRoleUpdateDTO.getId())
                            .setPermissionId(permissionId))
                    .toList();
            sysRolePermissionMapper.insertBatchSomeColumn(sysRolePermissions);
        }
    }

    public void deleteById(Long id) {
        if (sysUserRoleMapper.exists(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id))) {
            throw new ServiceException(1001, "删除失败，该角色被使用");
        }
        sysRoleMapper.deleteById(id);
    }

}
