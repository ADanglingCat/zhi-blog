## zhi-blog

### 项目介绍

> zhi-blog 是一个采用微服务架构的博客管理系统,参考mugu-blog 实现.

### 版本约定

| 依赖                 | 描述       | 版本         |
|:-------------------|:---------|:-----------|
| SpringBoot         | 容器/MVC框架 | 2.7.1      |
| SpringCloud        | 微服务框架    | 2021.0.3   |
| SpringCloudAlibaba | 微服务框架    | 2021.0.1.0 |
| Lombok             | 代码简化工具   | 1.18.24    |
| SpringDoc          | 接口文档文具   | 1.6.8      |
| AuthServer         | 授权服务     | 0.3.1      |

### 服务介绍

| 名称                   | 描述      | 端口   |
|:---------------------|:--------|:-----|
| zhi-blog-article     | 文章服务    | 9010 |
| zhi-blog-comment     | 评论服务    | 9011 |
| zhi-blog-user        | 用户/认证服务 | 9012 |
| zhi-blog-friend-link | 友链服务    | 9013 |
| zhi-blog-gateway     | 网关服务    | 9014 |
| zhi-blog-common      | 通用服务    | 9015 |
| zhi-blog-file-server | 文件存储服务  | 9016 |
| zhi-blog-picture     | 图片服务    | 9017 |
| zhi-blog-monitor     | 监控服务    | 9018 |

### 功能

- [x] 初始化项目,创建子模块
- [x] SpringDoc 接口文档
- [x] Logback 日志配置
- [x] 统一异常处理
- [ ] RabbitMq 配置
- [x] Redis 配置
- [x] MySQL 配置
- [x] Nacos 配置中心和注册中心
- [x] OpenFeign 服务调用
- [ ] OpenFeign 优化
- [x] Gateway网关统一处理请求
- [x] OAuth2 认证
- [ ] Sentinel 流量控制
- [ ] Skywalking链路追踪
- [ ] Spring Boot Admin监控服务
- [ ] Seata分布式事务
- [ ] ELK 日志收集
- [ ] Docker 容器
- [ ] K8S
- [ ] Gitlab ci/cd
- [ ] 前端配置
- [ ] 集群
- [ ] 部署云服务器



