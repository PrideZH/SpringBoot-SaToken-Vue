package cn.pridezh.rbac.mapper;

import cn.pridezh.rbac.domain.po.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author PrideZH
 * @since 2022/8/5 12:20
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}