package com.zhi.blog.article.api.service;

import com.zhi.blog.common.core.model.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "zhi-blog-gateway", contextId = "gateway-context")
public interface GatewayArticleService {

    @GetMapping("/zhi-blog-article/article")
    CommonResult getArticle();

    @PostMapping("/zhi-blog-article/article")
    CommonResult addArticle();
}