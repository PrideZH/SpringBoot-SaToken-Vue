package cn.pridezh.rbac.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author PrideZH
 * @since 2022/8/3 20:17
 */
@ApiModel("登录参数")
@Data
public class LoginDTO {

    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "账号不能为空")
    @Length(message = "账号长度必须在{min}到{max}之间", min = 4, max = 16)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    @Length(message = "密码长度必须在{min}到{max}之间", min = 6, max = 16)
    @Pattern(message = "密码不能包含特殊字符", regexp = "^[0-9a-zA-Z_]*$")
    private String password;

}
