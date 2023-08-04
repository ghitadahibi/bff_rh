package ma.pca.bff;

import ma.pca.starter.web.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Application
@SpringBootApplication
@ComponentScan("ma.pca.bff")
public class BffServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BffServiceApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "DELETE")
                        .allowedHeaders("Content-Type")
                        .exposedHeaders("Content-Type")
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

}