package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.po.Article;
import cn.pridezh.rbac.domain.vo.ArticleItemVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/5 14:29
 */
@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    ArticleItemVO toItemVO(Article article);

    List<ArticleItemVO> toItemVOList(List<Article> articles);

}