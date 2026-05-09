package com.abdelrahman.spokify.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMongoAuditing
public class AppConfig implements WebMvcConfigurer{

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // أي طلب بيبدأ بـ /audio/** هيروح يدور في فولدر public اللي عندك
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:./public/");
    }
	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
	       return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins(
	                                "http://localhost:5173",
	                                "http://127.0.0.1:5173"
	                        )
	                        .allowedMethods("*")
	                        .allowedHeaders("*");
	            }
	        };
	    }
}
