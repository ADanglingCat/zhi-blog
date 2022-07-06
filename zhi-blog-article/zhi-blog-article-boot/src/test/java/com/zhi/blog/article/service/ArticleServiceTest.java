package com.zhi.blog.article.service;

import com.zhi.blog.common.datasource.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    private RedisUtil redisUtil;
    @Test
    void test1() {
        var stringKey = "stringKey";
        var stringValue = "stringValue";
        redisUtil.set(stringKey, stringValue);
        Assert.isTrue(stringValue.equals(redisUtil.get(stringKey)), "get");
    }
}