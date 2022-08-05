package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.dto.SysRoleCreateDTO;
import cn.pridezh.rbac.domain.dto.SysRoleUpdateDTO;
import cn.pridezh.rbac.domain.po.SysRole;
import cn.pridezh.rbac.domain.vo.role.SysRoleItemVO;
import cn.pridezh.rbac.domain.vo.role.SysRoleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T13:03:41+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class SysRoleConvertImpl implements SysRoleConvert {

    @Override
    public SysRole toPO(SysRoleCreateDTO sysRoleCreateDTO) {
        if ( sysRoleCreateDTO == null ) {
            return null;
        }

        SysRole sysRole = new SysRole();

        sysRole.setName( sysRoleCreateDTO.getName() );

        return sysRole;
    }

    @Override
    public SysRole toPO(SysRoleUpdateDTO sysRoleUpdateDTO) {
        if ( sysRoleUpdateDTO == null ) {
            return null;
        }

        SysRole sysRole = new SysRole();

        sysRole.setId( sysRoleUpdateDTO.getId() );
        sysRole.setName( sysRoleUpdateDTO.getName() );

        return sysRole;
    }

    @Override
    public List<SysRoleVO> toVOList(List<SysRole> sysRoles) {
        if ( sysRoles == null ) {
            return null;
        }

        List<SysRoleVO> list = new ArrayList<SysRoleVO>( sysRoles.size() );
        for ( SysRole sysRole : sysRoles ) {
            list.add( sysRoleToSysRoleVO( sysRole ) );
        }

        return list;
    }

    @Override
    public List<SysRoleItemVO> toItemVOList(List<SysRole> sysRoles) {
        if ( sysRoles == null ) {
            return null;
        }

        List<SysRoleItemVO> list = new ArrayList<SysRoleItemVO>( sysRoles.size() );
        for ( SysRole sysRole : sysRoles ) {
            list.add( sysRoleToSysRoleItemVO( sysRole ) );
        }

        return list;
    }

    @Override
    public Page<SysRoleItemVO> toVOPage(IPage<SysRole> page) {
        if ( page == null ) {
            return null;
        }

        Page<SysRoleItemVO> page1 = new Page<SysRoleItemVO>();

        page1.setPages( page.getPages() );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );

        page1.setRecords( SysRoleConvert.INSTANCE.toItemVOList( page.getRecords() ) );

        return page1;
    }

    protected SysRoleVO sysRoleToSysRoleVO(SysRole sysRole) {
        if ( sysRole == null ) {
            return null;
        }

        SysRoleVO sysRoleVO = new SysRoleVO();

        sysRoleVO.setId( sysRole.getId() );
        sysRoleVO.setCreateTime( sysRole.getCreateTime() );
        sysRoleVO.setUpdateTime( sysRole.getUpdateTime() );
        sysRoleVO.setName( sysRole.getName() );

        return sysRoleVO;
    }

    protected SysRoleItemVO sysRoleToSysRoleItemVO(SysRole sysRole) {
        if ( sysRole == null ) {
            return null;
        }

        SysRoleItemVO sysRoleItemVO = new SysRoleItemVO();

        sysRoleItemVO.setId( sysRole.getId() );
        sysRoleItemVO.setCreateTime( sysRole.getCreateTime() );
        sysRoleItemVO.setUpdateTime( sysRole.getUpdateTime() );
        sysRoleItemVO.setName( sysRole.getName() );

        return sysRoleItemVO;
    }
}
