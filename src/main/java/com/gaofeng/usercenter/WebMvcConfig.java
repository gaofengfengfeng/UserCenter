package com.gaofeng.usercenter;

import com.didi.meta.javalib.JInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author:gaofeng
 * @Date:2018-05-23
 * @Description:
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JInterceptor()).addPathPatterns("/**");
    }
}
