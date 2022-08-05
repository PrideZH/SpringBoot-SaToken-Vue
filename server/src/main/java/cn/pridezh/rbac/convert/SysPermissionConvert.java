package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.po.SysPermission;
import cn.pridezh.rbac.domain.vo.SysPermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/4 22:05
 */
@Mapper
public interface SysPermissionConvert {

    SysPermissionConvert INSTANCE = Mappers.getMapper(SysPermissionConvert.class);

    List<SysPermissionVO> toVOList(List<SysPermission> sysPermissions);

}