package cn.pridezh.rbac.domain.vo.user;

import cn.pridezh.rbac.domain.common.BaseVO;
import cn.pridezh.rbac.domain.vo.role.SysRoleVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/4 22:35
 */
@ApiModel("角色列表信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserItemVO extends BaseVO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像URL")
    private String avatarUrl;

    @ApiModelProperty(value = "是否是超级管理员")
    private Boolean superAdmin;

    @ApiModelProperty(value = "角色")
    private List<SysRoleVO> roles;

}
