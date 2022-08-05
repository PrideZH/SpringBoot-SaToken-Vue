package cn.pridezh.rbac.service;

import cn.pridezh.rbac.domain.po.Article;
import cn.pridezh.rbac.mapper.ArticleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author PrideZH
 * @since 2022/8/5 12:21
 */
@AllArgsConstructor
@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {
}