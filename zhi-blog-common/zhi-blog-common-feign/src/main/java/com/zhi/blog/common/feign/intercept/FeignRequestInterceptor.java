package com.zhi.blog.common.feign.intercept;

import com.zhi.blog.common.feign.util.RequestUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Ted
 * @date 2022/5/20
 **/
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        var request = RequestUtil.getRequest();
        var headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            var key = headers.nextElement();
            if("Authorization".equalsIgnoreCase(key)) {
                var value = request.getHeader(key);
                template.header(key, value);
            }
        }
    }
}
