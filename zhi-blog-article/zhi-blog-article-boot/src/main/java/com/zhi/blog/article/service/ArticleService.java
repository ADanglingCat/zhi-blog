package com.zhi.blog.article.service;

import com.zhi.blog.common.datasource.util.RedisUtil;
import com.zhi.blog.common.service.model.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ted
 * @date 2022/6/17
 **/
@RequiredArgsConstructor
@Service
public class ArticleService extends BaseService {
    private final RedisUtil redisUtil;

}
