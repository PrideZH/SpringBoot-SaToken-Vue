package cn.pridezh.rbac.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 21:11
 */
@ApiModel("角色修改参数")
@Data
public class SysRoleUpdateDTO {

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long id;

    @ApiModelProperty(value = "名称")
    @Length(message = "名称长度不能大于{max}", max = 32)
    private String name;

    @ApiModelProperty(value = "权限ID集")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<Long> permissionIds;

}
