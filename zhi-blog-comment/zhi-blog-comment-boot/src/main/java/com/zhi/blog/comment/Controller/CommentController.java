package com.zhi.blog.comment.Controller;

import com.zhi.blog.article.api.service.ArticleService;
import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.model.TestModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private final ArticleService articleService;

    @GetMapping
    public CommonResult getComment(HttpServletRequest request, TestModel commonStatus) {
        log.info("getComment {}", request.getHeader("Authorization"));
        return articleService.getArticle(commonStatus);
    }

    @PostMapping
    public CommonResult addComment(@RequestBody TestModel commonStatus) {
        return articleService.addArticle(commonStatus);
    }
}
