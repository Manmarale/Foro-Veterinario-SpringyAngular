package com.manmarale.foro_veterinario.configuracion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionWeb {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        //.allowedOrigins("http://localhost:4200","http://foro-veterinario.com")
                        //.allowedOrigins("http://192.168.20.103:4200","http://foro-veterinario.com")
                        .allowedOrigins("http://localhost", "http://localhost:4200", "http://localhost", "http://frontend")
                        .allowedMethods("*")
                        .exposedHeaders("*")
                        .allowCredentials(true);  // <--- importante si envías cookies o cabeceras de autorización
            }
        };
    }
}
