package com.zhi.blog.auth.server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

/**
 * @author Ted
 * @date 2022/6/22
 **/
@RequiredArgsConstructor
@EnableWebSecurity
public class AuthorizationServerConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * token 增强
     * @return
     */
    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            Principal principal = context.getPrincipal();
            context.getHeaders().header("dfyH", context.getRegisteredClient().getClientId() + "dfyH");
            context.getClaims().claim("dfyC", "dfyClaim");
        };
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                //重定向至登录页
                .exceptionHandling(temp -> temp.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //spring security 配置
        return httpSecurity
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin(Customizer.withDefaults())
                .build();
    }

    /**
     * 白名单
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/actuator/**", "/druid/**");
    }

    /**
     * 配置客户信息存储
     * @param jdbcTemplate
     * @return
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        //dodo 2022/6/24 registeredClientRepository 客户端配置
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("default")
                .clientSecret(passwordEncoder().encode("Default@1024"))
                //clientAuthenticationMethod 授权服务器认证方式
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                //客户凭证凭证模式(clientId和clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                //授权码模式
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://127.0.0.1:9014/zhi-blog-auth-server/authorized")
                .redirectUri("http://127.0.0.1:9011/zhi-blog-comment/login/oauth2/code/default")
                .redirectUri("https://www.baidu.com")
                //作用域 默认需要添加openid支持/userinfo
                .scopes(strings -> strings.addAll(List.of("default", "openid")))
                //客户配置
                .clientSettings(ClientSettings.builder()
                        //无需用户确认自动授权
                        .requireAuthorizationConsent(false).build())
                //令牌配置
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofDays(1))
                        .refreshTokenTimeToLive(Duration.ofDays(3))
                        .reuseRefreshTokens(true).build())
                .build();
        var  repository = new JdbcRegisteredClientRepository(jdbcTemplate);
        if (repository.findByClientId("default") == null) {
            repository.save(registeredClient);
        }
        return repository;
    }

    /**
     * 配置授权信息存储
     * @param jdbcTemplate
     * @param repository
     * @return
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository repository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, repository);
    }

    /**
     * 授权记录存储
     * @param jdbcTemplate
     * @param repository
     * @return
     */
    @Bean
    public OAuth2AuthorizationConsentService auth2AuthorizationConsentService(JdbcTemplate jdbcTemplate,
                                                                              RegisteredClientRepository repository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, repository);
    }

    @Bean
    public UserDetailsService userDetailService() {
        //dodo 2022/6/20 loadUserByUsername db
        return new InMemoryUserDetailsManager(User.builder()
                .username("user")
                .password(passwordEncoder().encode("123"))
                .authorities("default")
                .build()
        );
    }

    /**
     * 配置授权服务器(端点路径,发布路径等)
     * @return
     */
    @Bean
    public ProviderSettings providerSettings() {
        //providerSettings 生产环境改成域名
        return ProviderSettings.builder()
                .issuer("http://dfy.com:9016/zhi-blog-auth-server")
                .build();
    }

    /**
     * keytool -genkeypair -alias ${alias} --keyalg RSA -keypass ${pass} -keystore ${alias}.jks -storepass ${pass}
     * keytool -list -rfc --keystore ${alias}.jks | openssl x509 -inform pem -pubkey
     * @return 获取rsa key
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        String alias = "zhi-blog";
        char[] pwd = "Nov2016".toCharArray();
        String fileName = "zhi-blog.jks";
        var keyStore = KeyStore.getInstance(new ClassPathResource(fileName).getFile(), pwd);
        var publicKey = ((RSAPublicKey) keyStore.getCertificate(alias).getPublicKey());
        var privateKey = ((PrivateKey) keyStore.getKey(alias, pwd));

        RSAKey rsaKey = new RSAKey
                .Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

//    @ConfigurationProperties(prefix = "spring.datasource.druid")
//    @Bean
//    public DataSource dataSource() {
//        DruidDataSource dataSource =  new DruidDataSource();
//        return dataSource;
//    }

}
