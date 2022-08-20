package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.po.SysPermission;
import cn.pridezh.rbac.domain.vo.SysPermissionVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/4 22:05
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SysPermissionConvert {

    public abstract List<SysPermissionVO> toVOList(List<SysPermission> sysPermissions);

}