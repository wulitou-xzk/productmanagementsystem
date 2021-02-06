package com.productmanage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // 添加视图映射，访问项目根路径或index.html时将映射到login.html页面
                registry.addViewController("/").setViewName("login");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginHandlerInterceptor())
                        .addPathPatterns("/**/*.html")  // 拦截所有.html路径
                        .excludePathPatterns("/",       // 添加不需要拦截的路径
                                "/**/*.css",
                                "/**/*.js",
                                "/**/*.txt",
                                "/**/*.png",
                                "/**/*.jpg",
                                "/scripts/*.js",
                                "/**/findPwd.html");
            }

        };
        return webMvcConfigurer;
    }

}
