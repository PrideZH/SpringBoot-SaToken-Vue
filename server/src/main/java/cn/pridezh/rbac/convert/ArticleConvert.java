package cn.pridezh.rbac.convert;

import cn.pridezh.rbac.domain.po.Article;
import cn.pridezh.rbac.domain.po.SysRole;
import cn.pridezh.rbac.domain.vo.ArticleItemVO;
import cn.pridezh.rbac.domain.vo.role.SysRoleItemVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/5 14:29
 */
@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    List<ArticleItemVO> toItemVOList(List<Article> articles);

    @Mapping(target = "records", expression = "java( ArticleConvert.INSTANCE.toItemVOList( page.getRecords() ) )")
    Page<ArticleItemVO> toVOPage(IPage<Article> page);

}