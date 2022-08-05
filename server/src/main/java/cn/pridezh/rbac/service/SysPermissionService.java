package cn.pridezh.rbac.service;

import cn.pridezh.rbac.domain.po.SysPermission;
import cn.pridezh.rbac.mapper.SysPermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author PrideZH
 * @since 2022/8/4 22:05
 */
@AllArgsConstructor
@Service
public class SysPermissionService extends ServiceImpl<SysPermissionMapper, SysPermission> {
}
