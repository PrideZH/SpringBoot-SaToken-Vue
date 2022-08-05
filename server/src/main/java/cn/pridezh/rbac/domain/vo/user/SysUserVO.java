package cn.pridezh.rbac.domain.vo.user;

import cn.pridezh.rbac.domain.common.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author PrideZH
 * @since 2022/8/3 20:59
 */
@ApiModel("用户信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserVO extends BaseVO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "是否是超级管理员")
    private Boolean superAdmin;

}
