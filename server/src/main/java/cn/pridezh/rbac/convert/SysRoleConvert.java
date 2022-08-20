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
@Mapper(componentModel = "spring")
public interface SysRoleConvert {

    SysRole toPO(SysRoleCreateDTO sysRoleCreateDTO);

    SysRole toPO(SysRoleUpdateDTO sysRoleUpdateDTO);

    List<SysRoleVO> toVOList(List<SysRole> sysRoles);

    SysRoleItemVO toItemVO(SysRole sysRole);

}