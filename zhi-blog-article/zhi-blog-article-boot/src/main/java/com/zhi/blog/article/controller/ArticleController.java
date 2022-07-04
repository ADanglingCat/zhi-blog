package com.zhi.blog.article.controller;

import com.zhi.blog.article.service.ArticleService;
import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.util.CoreUtil;
import com.zhi.blog.common.service.model.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ted
 * @date 2022/5/19
 **/
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
        CoreUtil.info("authorization", request.getHeader("Authorization"), request.getHeader("tempKey"));
        return CommonResult.success();
    }

    @PostMapping
    public CommonResult addArticle(HttpServletRequest request) {
        CoreUtil.info("version, name, Authorization", version, name, request.getHeader("Authorization"));
        return CommonResult.success(version + name);
    }

}
