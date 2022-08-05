package cn.pridezh.rbac.domain.vo.user;

import cn.pridezh.rbac.domain.common.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/4 19:03
 */
@ApiModel("用户详细信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserInfoVO extends BaseVO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "是否是超级管理员")
    private Boolean superAdmin;

    @ApiModelProperty(value = "权限")
    private List<String> permissions;

}
