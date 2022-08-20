package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.dto.SysRoleCreateDTO;
import cn.pridezh.rbac.domain.dto.SysRoleUpdateDTO;
import cn.pridezh.rbac.domain.po.SysRole;
import cn.pridezh.rbac.domain.vo.role.SysRoleItemVO;
import cn.pridezh.rbac.domain.vo.role.SysRoleVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 21:10
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SysRoleConvert {

    public abstract SysRole toPO(SysRoleCreateDTO sysRoleCreateDTO);

    public abstract SysRole toPO(SysRoleUpdateDTO sysRoleUpdateDTO);

    public abstract List<SysRoleVO> toVOList(List<SysRole> sysRoles);

    public abstract SysRoleItemVO toItemVO(SysRole sysRole);

}