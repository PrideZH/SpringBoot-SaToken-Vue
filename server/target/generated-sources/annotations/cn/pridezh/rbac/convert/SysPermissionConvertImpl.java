package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.po.SysPermission;
import cn.pridezh.rbac.domain.vo.SysPermissionVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T13:03:41+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class SysPermissionConvertImpl implements SysPermissionConvert {

    @Override
    public List<SysPermissionVO> toVOList(List<SysPermission> sysPermissions) {
        if ( sysPermissions == null ) {
            return null;
        }

        List<SysPermissionVO> list = new ArrayList<SysPermissionVO>( sysPermissions.size() );
        for ( SysPermission sysPermission : sysPermissions ) {
            list.add( sysPermissionToSysPermissionVO( sysPermission ) );
        }

        return list;
    }

    protected SysPermissionVO sysPermissionToSysPermissionVO(SysPermission sysPermission) {
        if ( sysPermission == null ) {
            return null;
        }

        SysPermissionVO sysPermissionVO = new SysPermissionVO();

        sysPermissionVO.setId( sysPermission.getId() );
        sysPermissionVO.setCreateTime( sysPermission.getCreateTime() );
        sysPermissionVO.setUpdateTime( sysPermission.getUpdateTime() );
        sysPermissionVO.setName( sysPermission.getName() );
        sysPermissionVO.setCode( sysPermission.getCode() );

        return sysPermissionVO;
    }
}
