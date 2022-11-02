package com.zhi.blog.article.controller;

import com.rabbitmq.client.Channel;
import com.zhi.blog.article.dao.ArticleMapper;
import com.zhi.blog.article.service.ArticleService;
import com.zhi.blog.common.core.model.CommonResult;
import com.zhi.blog.common.core.util.CoreUtil;
import com.zhi.blog.common.service.model.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Ted
 * @date 2022/5/19
 **/
@RequiredArgsConstructor
@RequestMapping("/article")
@RestController
public class ArticleController extends BaseController {
    private final ArticleService articleService;
    private final RabbitTemplate rabbitTemplate;
    private final ArticleMapper articleMapper;
    @Value("${config.version:0}")
    private String version;
    @Value("${config.name:name}")
    private String name;

    @GetMapping
    public CommonResult getArticle(HttpServletRequest request) {
        CoreUtil.info("authorization", request.getHeader("Authorization"), request.getHeader("tempKey"));
        var list = articleMapper.selectList(null);
        return CommonResult.success(list);
    }

    @PostMapping
    public CommonResult addArticle(HttpServletRequest request) {
        CoreUtil.info("version, name, Authorization", version, name, request.getHeader("Authorization"));
        return CommonResult.success(version + name);
    }

    @RabbitListener(queues = "article")
    public void onMessage(Message message, Channel channel) throws Exception {
        CoreUtil.info("message", message);
        //手动确认
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        CoreUtil.info("message ack end", message.getMessageProperties().getDeliveryTag());
    }

    private void send() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            CoreUtil.info("correlationData ack cause", correlationData, ack, cause);
        });
        rabbitTemplate.setReturnsCallback(returned -> {
            CoreUtil.info("returned", returned);
        });
        rabbitTemplate.convertAndSend("directExchange", "articleKey", CommonResult.success(), new CorrelationData(UUID.randomUUID().toString()));
        CoreUtil.info("send end");
    }

}
