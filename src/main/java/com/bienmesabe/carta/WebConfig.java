package com.bienmesabe.carta;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /** Las fotos van en la carpeta ./img junto al programa (se pueden cambiar sin recompilar). */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry r) {
        r.addResourceHandler("/img/**").addResourceLocations("file:./img/");
    }

    /** La carta es pública; /admin pide usuario y contraseña. */
    @Bean
    SecurityFilterChain seguridad(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(a -> a.requestMatchers("/admin/**").authenticated().anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
