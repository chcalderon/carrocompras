package com.backprinc.rps.usersapp.backendusersapp.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.backprinc.rps.usersapp.backendusersapp.auth.filters.JwtAuthenticationFilter;
import com.backprinc.rps.usersapp.backendusersapp.auth.filters.JwtValidationFilter;




@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                                // .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/users/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowedOrigins(Arrays.asList("http://localhost:5175"));
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    // @Bean
    // SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     return http.authorizeHttpRequests()
    //             .requestMatchers(HttpMethod.GET, "/users").permitAll()
    //             .anyRequest().authenticated()
    //             .and()
    //             .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
    //             .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
    //             .csrf(config -> config.disable())
    //             .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .build();

    // }
}
