package cn.pridezh.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.pridezh.rbac.convert.ArticleConvert;
import cn.pridezh.rbac.domain.common.Result;
import cn.pridezh.rbac.domain.dto.ArticleDTO;
import cn.pridezh.rbac.domain.dto.PageDTO;
import cn.pridezh.rbac.domain.po.Article;
import cn.pridezh.rbac.domain.vo.ArticleItemVO;
import cn.pridezh.rbac.service.ArticleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author PrideZH
 * @since 2022/8/5 12:21
 */
@Api(tags = "文章接口")
@AllArgsConstructor
@RestController
@RequestMapping("/article")
public class ArticleController {

    private ArticleService articleService;

    @ApiOperation(value = "创建文章")
    @SaCheckPermission("article:add")
    @PostMapping("")
    public Result<Void> post(@Validated @RequestBody ArticleDTO articleDTO) {
        Article article = new Article();
        article.setContent(articleDTO.getContent());
        articleService.save(article);
        return Result.success(null);
    }

    @ApiOperation(value = "查询文章列表")
    @SaCheckPermission("article:get")
    @GetMapping("/page")
    public Result<IPage<ArticleItemVO>> listByPage(@Validated PageDTO pageDTO) {
        Page<Article> page = articleService.page(new Page<>(pageDTO.getPage(), pageDTO.getSize()));
        return Result.success(page.convert(ArticleConvert.INSTANCE::toItemVO));
    }

    @ApiOperation(value = "修改文章")
    @SaCheckPermission("article:put")
    @PutMapping("/{id:\\d+}")
    public Result<Void> put(
            @PathVariable("id") String id,
            @Validated @RequestBody ArticleDTO articleDTO) {
        Article article = new Article();
        article.setId(Long.valueOf(id));
        article.setContent(articleDTO.getContent());
        articleService.updateById(article);
        return Result.success(null);
    }

    @ApiOperation(value = "删除文章")
    @SaCheckPermission("article:del")
    @DeleteMapping("/{id:\\d+}")
    public Result<Void> delete(@PathVariable("id") String id) {
        articleService.removeById(id);
        return Result.success(null);
    }

}
