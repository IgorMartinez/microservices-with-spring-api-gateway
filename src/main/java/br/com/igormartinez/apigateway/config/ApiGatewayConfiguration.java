package br.com.igormartinez.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(
                p -> p.path("/get")
                    .filters(f -> f.addRequestHeader("Hello", "World"))
                    .uri("http://httpbin.org:80")
            )
            .route(p -> p.path("/cambio/**").uri("lb://cambio-service"))
            .route(p -> p.path("/book/**").uri("lb://book-service"))
            .build();
    }
}
