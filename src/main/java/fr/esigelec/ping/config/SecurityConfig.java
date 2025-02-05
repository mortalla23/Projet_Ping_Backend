package fr.esigelec.ping.config;

//import fr.esigelec.ping.config.CustomAuthorizationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@Configuration
public class SecurityConfig {


private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizationManager.class);
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthorizationManager customAuthorizationManager;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthorizationManager customAuthorizationManager) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthorizationManager = customAuthorizationManager;
    }

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
        // Ajouter le filtre JWT avant le filtre de gestion d'authentification classique
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
       // Remplace tes System.out.print par :
        logger.info("Message de débogage ici");
        http
            /*.requiresChannel(channel -> channel
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") == null
                        || !r.getHeader("X-Forwarded-Proto").equals("https")
                )
                .requiresSecure())  // Forcer l'utilisation du canal sécurisé
            */.csrf(csrf -> csrf.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
            .requestMatchers( "/api/users/**", "/api/users/*", "/api/link/**","/ws","/ws2").permitAll()  // Permettre les GET
            .requestMatchers(HttpMethod.GET,  "/api/user-documents/**", "/api/ppre/*", "/api/ppre/**",  "/api/historique-education/**", "/api/historique-education/*", "/api/historique-sante/**","/api/historique-sante/*")
                .access(customAuthorizationManager) // Utiliser le CustomAuthorizationManager pour les GET
    
            .anyRequest().authenticated()
                    )
            .headers(headers -> headers.frameOptions(Customizer.withDefaults()))
            .formLogin(login -> login.disable());
        
        http.setSharedObject(HttpFirewall.class, strictHttpFirewall());
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Remplace tes System.out.print par :
logger.info("Message de débogage ici");
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5200","http://localhost:3000", "http://localhost:5200","https://localhost:3000") // Autorise toutes les origines
                        .allowedMethods("*")  // Autorise toutes les méthodes HTTP
                        .allowedHeaders("*")  // Autorise tous les headers
                        .allowCredentials(true);
            }
        };
    }
}
