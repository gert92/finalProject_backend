package com.seg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SdaTravelAgencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdaTravelAgencyApplication.class, args);

    }

    @Bean
    public WebMvcConfigurer corsConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000/", "https://final-project-frontend-ten.vercel.app/").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

}
