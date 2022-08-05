package cn.pridezh.rbac.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 21:11
 */
@ApiModel("角色创建参数")
@Data
public class SysRoleCreateDTO {

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    @Length(message = "名称长度不能大于{max}", max = 32)
    private String name;

    @ApiModelProperty(value = "权限ID集")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<Long> permissionIds;

}
