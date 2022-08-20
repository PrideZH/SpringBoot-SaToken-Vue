package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.po.Article;
import cn.pridezh.rbac.domain.vo.ArticleItemVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/5 14:29
 */
@Mapper(componentModel = "spring")
public interface ArticleConvert {

    ArticleItemVO toItemVO(Article article);

    List<ArticleItemVO> toItemVOList(List<Article> articles);

}