package br.com.igormartinez.apigateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OpenAPIConfiguration {
    
    @Bean
    @Lazy(false)
    List<GroupedOpenApi> apis(SwaggerUiConfigParameters config, RouteDefinitionLocator locator) {
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();

        List<GroupedOpenApi> groups = new ArrayList<>();

        definitions.stream()
            .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
            .forEach(routeDefinition -> {
                config.addGroup(routeDefinition.getId());

                groups.add(
                    GroupedOpenApi.builder()
                        .pathsToMatch("/" + routeDefinition.getId() + "/**")
                        .group(routeDefinition.getId())
                        .build()
                );
            });

        return groups;
    }
}
