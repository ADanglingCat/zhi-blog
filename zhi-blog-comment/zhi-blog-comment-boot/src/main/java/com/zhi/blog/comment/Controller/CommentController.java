package com.zhi.blog.comment.Controller;

import com.zhi.blog.article.api.service.ArticleService;
import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.util.CoreUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ted
 * @date 2022/5/20
 **/
@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {
    private final ArticleService articleService;

    @GetMapping
    public CommonResult getComment(HttpServletRequest request) {
        CoreUtil.info("Authorization", request.getHeader("Authorization"));
        return articleService.getArticle();
    }

    @PostMapping
    public CommonResult addComment() {
        return articleService.addArticle();
    }
}
