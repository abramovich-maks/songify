package com.songify.infrastructure.security;

import com.songify.domain.usercrud.UserConformer;
import com.songify.domain.usercrud.UserDetailsServiceImpl;
import com.songify.domain.usercrud.UserRepository;
import com.songify.infrastructure.security.jwt.oauth2.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    public UserDetailsManager userDetailsService(UserRepository userRepository, UserConformer userConformer) {
        return new UserDetailsServiceImpl(userRepository, passwordEncoder(), userConformer);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            JwtAuthConverter converter,
                                            MySuccessHandler mySuccessHandler,
            /* JwtAuthTokenFilter jwtAuthTokenFilter, */
                                            CookieTokenResolver cookieTokenResolver) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .csrf(c -> c.disable())
//                .cors(corsConfigurerCustomizer())
                .formLogin(c -> c.disable())
                .httpBasic(c -> c.disable())
                .oauth2Login(oauth -> oauth.successHandler(mySuccessHandler))
                .oauth2ResourceServer(rs -> rs
                        .bearerTokenResolver(cookieTokenResolver)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(converter))
                )
//                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/users/register/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/token/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/message").hasRole("ADMIN")
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
                        .requestMatchers("/users/confirm/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    public Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("https://localhost:5174"));
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                return config;
            };
            c.configurationSource(source);
        };
    }
}
