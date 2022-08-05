package cn.pridezh.rbac.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author PrideZH
 * @since 2022/8/5 12:24
 */
@ApiModel("文章参数")
@Data
public class ArticleDTO {

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    @Length(message = "内容长度不能大于{max}", max = 200)
    private String content;

}