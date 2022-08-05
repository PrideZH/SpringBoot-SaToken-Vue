package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.po.Article;
import cn.pridezh.rbac.domain.vo.ArticleItemVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T14:31:16+0800",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
public class ArticleConvertImpl implements ArticleConvert {

    @Override
    public List<ArticleItemVO> toItemVOList(List<Article> articles) {
        if ( articles == null ) {
            return null;
        }

        List<ArticleItemVO> list = new ArrayList<ArticleItemVO>( articles.size() );
        for ( Article article : articles ) {
            list.add( articleToArticleItemVO( article ) );
        }

        return list;
    }

    @Override
    public Page<ArticleItemVO> toVOPage(IPage<Article> page) {
        if ( page == null ) {
            return null;
        }

        Page<ArticleItemVO> page1 = new Page<ArticleItemVO>();

        page1.setPages( page.getPages() );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );

        page1.setRecords( ArticleConvert.INSTANCE.toItemVOList( page.getRecords() ) );

        return page1;
    }

    protected ArticleItemVO articleToArticleItemVO(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleItemVO articleItemVO = new ArticleItemVO();

        articleItemVO.setId( article.getId() );
        articleItemVO.setCreateTime( article.getCreateTime() );
        articleItemVO.setUpdateTime( article.getUpdateTime() );
        articleItemVO.setContent( article.getContent() );

        return articleItemVO;
    }
}
