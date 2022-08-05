package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.dto.SysRoleCreateDTO;
import cn.pridezh.rbac.domain.dto.SysRoleUpdateDTO;
import cn.pridezh.rbac.domain.po.SysRole;
import cn.pridezh.rbac.domain.vo.role.SysRoleItemVO;
import cn.pridezh.rbac.domain.vo.role.SysRoleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 21:10
 */
@Mapper
public interface SysRoleConvert {

    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRole toPO(SysRoleCreateDTO sysRoleCreateDTO);

    SysRole toPO(SysRoleUpdateDTO sysRoleUpdateDTO);

    List<SysRoleVO> toVOList(List<SysRole> sysRoles);

    List<SysRoleItemVO> toItemVOList(List<SysRole> sysRoles);

    @Mapping(target = "records", expression = "java( SysRoleConvert.INSTANCE.toItemVOList( page.getRecords() ) )")
    Page<SysRoleItemVO> toVOPage(IPage<SysRole> page);

}