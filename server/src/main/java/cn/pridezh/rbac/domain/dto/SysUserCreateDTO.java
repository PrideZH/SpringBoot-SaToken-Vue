package cn.pridezh.rbac.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 21:02
 */
@ApiModel("用户创建参数")
@Data
public class SysUserCreateDTO {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "名称不能为空")
    @Length(message = "账号长度必须在{min}到{max}之间", min = 4, max = 16)
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "名称不能为空")
    @Length(message = "密码长度必须在{min}到{max}之间", min = 6, max = 16)
    @Pattern(message = "密码不能包含特殊字符", regexp = "^[0-9a-zA-Z_]*$")
    private String password;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message = "名称不能为空")
    @Length(message = "名称长度不能大于{max}", max = 32)
    private String nickname;

    @ApiModelProperty(value = "角色ID集")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<Long> roleIds;

}
