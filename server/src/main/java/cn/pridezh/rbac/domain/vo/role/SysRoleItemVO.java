package cn.pridezh.rbac.domain.vo.role;

import cn.pridezh.rbac.domain.common.BaseVO;
import cn.pridezh.rbac.domain.vo.SysPermissionVO;
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
public class SysRoleItemVO extends BaseVO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "权限")
    private List<SysPermissionVO> permissions;

}
