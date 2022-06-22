package com.zhi.blog.comment.Controller;

import com.zhi.blog.article.api.service.GatewayArticleService;
import com.zhi.blog.common.core.model.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ted
 * @date 2022/5/20
 **/
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {
    private final GatewayArticleService articleService;

    @GetMapping
    public CommonResult getComment(HttpServletRequest request) {
        log.info("getComment {}", request.getHeader("Authorization"));
        return articleService.getArticle();
    }

    @PostMapping
    public CommonResult addComment() {
        return articleService.addArticle();
    }
}
