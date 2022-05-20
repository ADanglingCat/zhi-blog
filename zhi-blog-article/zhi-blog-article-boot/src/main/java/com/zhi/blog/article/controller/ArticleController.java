package com.zhi.blog.article.controller;

import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.model.TestModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ted
 * @date 2022/5/19
 **/
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping
    public CommonResult getArticle(HttpServletRequest request, TestModel commonStatus) {
        log.info("test {}", request.getHeader("Authorization"));
        return CommonResult.success(commonStatus);
    }

    @PostMapping
    public CommonResult addArticle(@RequestBody TestModel commonStatus) {
        return CommonResult.success(commonStatus);
    }

}
