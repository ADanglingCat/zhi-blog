package com.zhi.blog.gateway.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.event.RefreshRoutesResultEvent;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ted
 * @date 2022/6/9
 **/
@Configuration
public class GatewaySwaggerResourcesProvider implements ApplicationListener<RefreshRoutesResultEvent> {
    private final SwaggerUiConfigParameters swaggerUiConfigParameters;
    private final RouteLocator routeLocator;
    private final List<String> blackList = new CopyOnWriteArrayList<>();

    public GatewaySwaggerResourcesProvider(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteLocator routeLocator) {
        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
        this.routeLocator = routeLocator;
        blackList.add("open-api");
    }

    @Override
    public void onApplicationEvent(RefreshRoutesResultEvent event) {
        routeLocator.getRoutes().subscribe(route -> {
            String name = route.getId();
            if(!blackList.contains(name)) {
                swaggerUiConfigParameters.addGroup(name);
                GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
                blackList.add(name);
            }
        });
    }
}
