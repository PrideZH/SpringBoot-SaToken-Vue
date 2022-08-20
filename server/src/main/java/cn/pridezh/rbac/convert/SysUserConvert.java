package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.dto.SysUserCreateDTO;
import cn.pridezh.rbac.domain.dto.SysUserUpdateDTO;
import cn.pridezh.rbac.domain.po.SysUser;
import cn.pridezh.rbac.domain.vo.user.SysUserInfoVO;
import cn.pridezh.rbac.domain.vo.user.SysUserItemVO;
import org.mapstruct.Mapper;

/**
 * @author PrideZH
 * @since 2022/8/3 20:58
 */
@Mapper(componentModel = "spring")
public interface SysUserConvert {

    SysUser toPO(SysUserCreateDTO sysUserCreateDTO);

    SysUser toPO(SysUserUpdateDTO sysUserUpdateDTO);

    SysUserInfoVO toVO(SysUser sysUser);

    SysUserItemVO toItemVO(SysUser sysUser);

}