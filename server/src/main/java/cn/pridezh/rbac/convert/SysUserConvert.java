package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.dto.SysUserCreateDTO;
import cn.pridezh.rbac.domain.dto.SysUserUpdateDTO;
import cn.pridezh.rbac.domain.po.SysUser;
import cn.pridezh.rbac.domain.vo.user.SysUserInfoVO;
import cn.pridezh.rbac.domain.vo.user.SysUserItemVO;
import cn.pridezh.rbac.domain.vo.user.SysUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 20:58
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser toPO(SysUserCreateDTO sysUserCreateDTO);

    SysUser toPO(SysUserUpdateDTO sysUserUpdateDTO);

    SysUserInfoVO toVO(SysUser sysUser);

    List<SysUserVO> toVOList(List<SysUser> sysUsers);

    List<SysUserItemVO> toItemVOList(List<SysUser> sysUsers);

    @Mapping(target = "records", expression = "java( SysUserConvert.INSTANCE.toItemVOList( page.getRecords() ) )")
    Page<SysUserItemVO> toVOPage(IPage<SysUser> page);

}