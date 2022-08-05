package cn.pridezh.rbac.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author PrideZH
 * @since 2022/8/3 20:16
 */
@ApiModel("令牌信息")
@Data
public class TokenVO {

    @ApiModelProperty("令牌Header")
    private String name;

    @ApiModelProperty("令牌Value")
    private String value;

}