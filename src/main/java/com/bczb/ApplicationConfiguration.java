package com.bczb;

import com.bczb.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

//	@Bean
//	public HandlerInterceptor getInterceptor(){
//		return new AuthInterceptor();
//	}

	@Override
	public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowedOriginPatterns("*");

	}

	//配置拦截器
	@Override
	public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
		registry.addInterceptor(new com.bczb.interceptor.AuthInterceptor())
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/signin/**");
	}

    
}
