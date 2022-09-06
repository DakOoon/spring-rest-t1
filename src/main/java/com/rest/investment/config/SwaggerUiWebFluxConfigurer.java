package com.rest.investment.config;

import org.springframework.util.StringUtils;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/*
 * For non-boot applications springfox-swagger-ui is no longer automatically enabled by adding
 * the dependency. It needs to be explicitly register using a resource handler configurer
 * (WebFluxConfigurer or WebMvcConfigurer). Here is how it is configured in springfox-boot-starter
 */
public class SwaggerUiWebFluxConfigurer implements WebFluxConfigurer {
  private final String baseUrl;

  public SwaggerUiWebFluxConfigurer(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
    registry.
        addResourceHandler(baseUrl + "/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
        .resourceChain(false);
  }
}