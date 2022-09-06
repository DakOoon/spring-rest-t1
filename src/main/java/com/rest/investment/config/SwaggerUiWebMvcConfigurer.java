package com.rest.investment.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * For non-boot applications springfox-swagger-ui is no longer automatically enabled by adding
 * the dependency. It needs to be explicitly register using a resource handler configurer
 * (WebFluxConfigurer or WebMvcConfigurer). Here is how it is configured in springfox-boot-starter
 */
public class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {
    private final String baseUrl;
  
    public SwaggerUiWebMvcConfigurer(String baseUrl) {
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
  
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController(baseUrl + "/swagger-ui/")
          .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
    }
  }