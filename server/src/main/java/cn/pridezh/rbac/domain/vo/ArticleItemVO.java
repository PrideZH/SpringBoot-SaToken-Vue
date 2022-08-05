package cn.pridezh.rbac.domain.vo;

import cn.pridezh.rbac.domain.common.BaseVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author PrideZH
 * @since 2022/8/5 14:28
 */
@ApiModel("文章信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArticleItemVO extends BaseVO {

    private String content;

}