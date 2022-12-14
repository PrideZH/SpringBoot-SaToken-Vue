package cn.pridezh.rbac.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author PrideZH
 * @since 2022/8/3 20:55
 */
@ApiModel("分页查询参数")
@Data
public class PageDTO {

    @ApiModelProperty("第几页")
    @NotNull(message = "缺少 page")
    @Min(value = 1, message = "page 必须大于等于 1")
    private Integer page;

    @ApiModelProperty("每页数量")
    @NotNull(message = "缺少 size")
    @Min(value = 1, message = "size 必须大于等于 1")
    private Integer size;

}