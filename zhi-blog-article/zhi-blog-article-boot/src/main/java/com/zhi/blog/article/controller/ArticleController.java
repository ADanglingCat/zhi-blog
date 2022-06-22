package com.zhi.blog.article.controller;

import com.zhi.blog.article.service.ArticleService;
import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.service.model.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ted
 * @date 2022/5/19
 **/
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/article")
@RestController
public class ArticleController extends BaseController {
    private final ArticleService articleService;

    @Value("${config.version:0}")
    private String version;
    @Value("${config.name:name}")
    private String name;

    @GetMapping
    public CommonResult getArticle(HttpServletRequest request, @RequestHeader("tokeInfo") String tokenInfo) {
        log.info("test {}", request.getHeader("Authorization"));
        return CommonResult.success();
    }

    @PostMapping
    public CommonResult addArticle(@RequestHeader("tokeInfo") String tokenInfo) {
        log.info("addArticle {} {} {}", version, name, tokenInfo);
        return CommonResult.success(version + name + tokenInfo);
    }

}
