package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.dto.SysUserCreateDTO;
import cn.pridezh.rbac.domain.dto.SysUserUpdateDTO;
import cn.pridezh.rbac.domain.po.SysUser;
import cn.pridezh.rbac.domain.vo.user.SysUserInfoVO;
import cn.pridezh.rbac.domain.vo.user.SysUserItemVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author PrideZH
 * @since 2022/8/3 20:58
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SysUserConvert {

    @Value("${minio.url}")
    protected String url;

    @Value("${minio.bucket}")
    protected String bucket;

    public abstract SysUser toPO(SysUserCreateDTO sysUserCreateDTO);

    public abstract SysUser toPO(SysUserUpdateDTO sysUserUpdateDTO);

    @Mapping(source = "avatarUrl", target = "avatarUrl", qualifiedByName = "avatarPrefix")
    public abstract SysUserInfoVO toVO(SysUser sysUser);

    @Mapping(source = "avatarUrl", target = "avatarUrl", qualifiedByName = "avatarPrefix")
    public abstract SysUserItemVO toItemVO(SysUser sysUser);

    @Named("avatarPrefix")
    public String avatarPrefix(String avatarUrl) {
        return url + "/" + bucket + "/" + avatarUrl;
    }

}