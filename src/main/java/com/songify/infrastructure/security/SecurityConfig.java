package com.songify.infrastructure.security;

import com.songify.domain.usercrud.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsManager userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository, passwordEncoder());
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .cors(c -> corsConfigurerCustomizer())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/users/register/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/artists/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/genres/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/albums/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/songs/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/songs/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/songs/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/songs/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/artists/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/artists/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/artists/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/artists/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/genres/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/albums/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/albums/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/albums/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/albums/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    public Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("http://localhost:3000"));
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            };
            c.configurationSource(source);
        };
    }
}
