package cn.pridezh.rbac.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/3 21:04
 */
@Data
public class SysUserUpdateDTO {

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long id;

    @ApiModelProperty(value = "昵称")
    @Length(message = "名称长度不能大于{max}", max = 32)
    private String nickname;

    @ApiModelProperty(value = "角色ID集")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<Long> roleIds;

    @ApiModelProperty(value = "头像URL")
    private String avatarUrl;

}
