package com.example.vibeapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS(Cross-Origin Resource Sharing) 설정을 위한 클래스
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 설정을 적용
        registry.addMapping("/**")
                // 프론트엔드 출처(http://localhost:3000) 허용
                .allowedOrigins("http://localhost:3000")
                // 허용할 HTTP 메서드 명시
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 모든 헤더 허용
                .allowedHeaders("*")
                // 인증 정보(Cookie 등) 포함 허용
                .allowCredentials(true);
    }
}
