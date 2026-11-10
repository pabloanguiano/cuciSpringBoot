package security.jpa.ApiRest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import security.jpa.ApiRest.service.JpaUserDetailService;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {


private  JpaUserDetailService jpaUserDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // <--- Asegúrate de tener esto declarado aquí arriba

    public SecurityConfig(JpaUserDetailService jpaUserDetailService) {
        this.jpaUserDetailService = jpaUserDetailService;
    }

//@Bean
//    InMemoryUserDetailsManager User(){
//    return new InMemoryUserDetailsManager(
//            User.withUsername("pablo")
//                    .password("{noop}pass")
//                    .roles("user")
//                    .build()
//    );
//}
//@Override
//public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/api/users")
//            .allowedOrigins("http://localhost:5173") // Frontend URL
//            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
//}
//


//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        return http
//                //.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic(withDefaults())
//                .headers(headers ->  headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
//                .build();
//    }
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager(); // este es para gestionar los permisis desde el controlador auth
}

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Clears the default "ROLE_" prefix
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
               // .httpBasic(withDefaults())
                .httpBasic(httpBasic -> httpBasic.disable())// Desactiva el HTTP Basic por completo para cuando cambia a https on server y activar jwt
                .cors(Customizer.withDefaults()) // Tells Spring Security to use our corsConfigurationSource Bean
                .csrf(csrf -> csrf.disable()) // Optional: Disable CSRF if testing locally
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/api/auth/**", "/login").permitAll()
                        .anyRequest().authenticated())

        // JWT trabaja sin estado (sin sesiones de servidor)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Insertamos nuestro filtro de JWT antes del filtro de autenticación estándar
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(
                // Accesos por IP
                "http://3.18.84.123",
                "http://3.18.84.123:80",

                // Accesos por Dominio (HTTP)
                "http://pmsoft.com.mx",
                "http://www.pmsoft.com.mx",

                // Accesos por Dominio (HTTPS - para cuando activemos el SSL)
                "https://pmsoft.com.mx",
                "https://www.pmsoft.com.mx",

                // Entorno local de desarrollo (por si haces pruebas desde tu PC)
                "http://localhost:5173",
                "http://localhost:3000",
                "http://localhost:5173",
                "https://*.tunnelmole.net",  // <-- Acepta cualquier subdominio seguro de tunnelmole
                "http://*.tunnelmole.net"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
/*
   @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

*/
}
