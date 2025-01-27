package fr.esigelec.ping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall strictHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true); // Permet "/"
        firewall.setAllowUrlEncodedPercent(true); // Permet "%"
        firewall.setAllowSemicolon(true); // Permet ";"
        return firewall;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors() // ➡️ Active CORS ici
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                .anyRequest().permitAll())
            .headers().frameOptions().sameOrigin()
            .and()
            .formLogin().disable();

        http.setSharedObject(HttpFirewall.class, strictHttpFirewall());
        return http.build();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5200","http://localhost:3000", "http://localhost:5200","https://localhost:3000")   // Autorise toutes les origines
                        .allowedMethods("*")  // Autorise toutes les méthodes HTTP
                        .allowedHeaders("*")  // Autorise tous les headers
                        .allowCredentials(true);
            }
        };
    }
}
