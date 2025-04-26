package com.example.demo.config;

import com.example.demo.interceptor.BitacoraInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final BitacoraInterceptor bitacoraInterceptor;

    @Value("${bitacora.interceptor.enabled:false}")
    private boolean interceptorEnabled;

    @Autowired
    public WebMvcConfig(BitacoraInterceptor bitacoraInterceptor) {
        this.bitacoraInterceptor = bitacoraInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (interceptorEnabled) {
            registry.addInterceptor(bitacoraInterceptor).addPathPatterns("/**");
        }
    }
}
