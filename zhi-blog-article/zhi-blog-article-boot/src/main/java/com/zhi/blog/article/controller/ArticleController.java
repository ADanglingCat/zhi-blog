package com.zhi.blog.article.controller;

import com.zhi.blog.article.service.ArticleService;
import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.service.model.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult getArticle(HttpServletRequest request) {
        log.info("test {}", request.getHeader("Authorization"));
        return CommonResult.success();
    }

    @PostMapping
    public CommonResult addArticle() {
        log.info("addArticle {} {}", version, name);
        return CommonResult.success(version + name);
    }

}
