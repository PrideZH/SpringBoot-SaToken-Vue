package cn.pridezh.rbac.domain.vo.role;

import cn.pridezh.rbac.domain.common.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author PrideZH
 * @since 2022/8/3 21:11
 */
@ApiModel("角色信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRoleVO extends BaseVO {

    @ApiModelProperty(value = "名称")
    private String name;

}
