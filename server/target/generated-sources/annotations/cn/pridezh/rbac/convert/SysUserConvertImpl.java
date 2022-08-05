package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.dto.SysUserCreateDTO;
import cn.pridezh.rbac.domain.dto.SysUserUpdateDTO;
import cn.pridezh.rbac.domain.po.SysUser;
import cn.pridezh.rbac.domain.vo.user.SysUserInfoVO;
import cn.pridezh.rbac.domain.vo.user.SysUserItemVO;
import cn.pridezh.rbac.domain.vo.user.SysUserVO;
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
public class SysUserConvertImpl implements SysUserConvert {

    @Override
    public SysUser toPO(SysUserCreateDTO sysUserCreateDTO) {
        if ( sysUserCreateDTO == null ) {
            return null;
        }

        SysUser sysUser = new SysUser();

        sysUser.setUsername( sysUserCreateDTO.getUsername() );
        sysUser.setPassword( sysUserCreateDTO.getPassword() );
        sysUser.setNickname( sysUserCreateDTO.getNickname() );

        return sysUser;
    }

    @Override
    public SysUser toPO(SysUserUpdateDTO sysUserUpdateDTO) {
        if ( sysUserUpdateDTO == null ) {
            return null;
        }

        SysUser sysUser = new SysUser();

        sysUser.setId( sysUserUpdateDTO.getId() );
        sysUser.setNickname( sysUserUpdateDTO.getNickname() );

        return sysUser;
    }

    @Override
    public SysUserInfoVO toVO(SysUser sysUser) {
        if ( sysUser == null ) {
            return null;
        }

        SysUserInfoVO sysUserInfoVO = new SysUserInfoVO();

        sysUserInfoVO.setId( sysUser.getId() );
        sysUserInfoVO.setCreateTime( sysUser.getCreateTime() );
        sysUserInfoVO.setUpdateTime( sysUser.getUpdateTime() );
        sysUserInfoVO.setUsername( sysUser.getUsername() );
        sysUserInfoVO.setNickname( sysUser.getNickname() );
        sysUserInfoVO.setSuperAdmin( sysUser.getSuperAdmin() );

        return sysUserInfoVO;
    }

    @Override
    public List<SysUserVO> toVOList(List<SysUser> sysUsers) {
        if ( sysUsers == null ) {
            return null;
        }

        List<SysUserVO> list = new ArrayList<SysUserVO>( sysUsers.size() );
        for ( SysUser sysUser : sysUsers ) {
            list.add( sysUserToSysUserVO( sysUser ) );
        }

        return list;
    }

    @Override
    public List<SysUserItemVO> toItemVOList(List<SysUser> sysUsers) {
        if ( sysUsers == null ) {
            return null;
        }

        List<SysUserItemVO> list = new ArrayList<SysUserItemVO>( sysUsers.size() );
        for ( SysUser sysUser : sysUsers ) {
            list.add( sysUserToSysUserItemVO( sysUser ) );
        }

        return list;
    }

    @Override
    public Page<SysUserItemVO> toVOPage(IPage<SysUser> page) {
        if ( page == null ) {
            return null;
        }

        Page<SysUserItemVO> page1 = new Page<SysUserItemVO>();

        page1.setPages( page.getPages() );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );

        page1.setRecords( SysUserConvert.INSTANCE.toItemVOList( page.getRecords() ) );

        return page1;
    }

    protected SysUserVO sysUserToSysUserVO(SysUser sysUser) {
        if ( sysUser == null ) {
            return null;
        }

        SysUserVO sysUserVO = new SysUserVO();

        sysUserVO.setId( sysUser.getId() );
        sysUserVO.setCreateTime( sysUser.getCreateTime() );
        sysUserVO.setUpdateTime( sysUser.getUpdateTime() );
        sysUserVO.setUsername( sysUser.getUsername() );
        sysUserVO.setNickname( sysUser.getNickname() );
        sysUserVO.setSuperAdmin( sysUser.getSuperAdmin() );

        return sysUserVO;
    }

    protected SysUserItemVO sysUserToSysUserItemVO(SysUser sysUser) {
        if ( sysUser == null ) {
            return null;
        }

        SysUserItemVO sysUserItemVO = new SysUserItemVO();

        sysUserItemVO.setId( sysUser.getId() );
        sysUserItemVO.setCreateTime( sysUser.getCreateTime() );
        sysUserItemVO.setUpdateTime( sysUser.getUpdateTime() );
        sysUserItemVO.setUsername( sysUser.getUsername() );
        sysUserItemVO.setNickname( sysUser.getNickname() );
        sysUserItemVO.setSuperAdmin( sysUser.getSuperAdmin() );

        return sysUserItemVO;
    }
}
