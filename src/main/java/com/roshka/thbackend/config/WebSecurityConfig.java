package com.roshka.thbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/thbackend/auth/login", "/thbackend/auth/register");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize.requestMatchers(HttpMethod.GET, "/thbackend/**").permitAll()
                                .requestMatchers("/thbackend/auth/**").permitAll()
                                .requestMatchers("/thbackend/**").permitAll()
                                .anyRequest().authenticated()


                );

        return http.build();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/thbackend/auth/api/"); // Definir un RequestMatcher para las URLs que requieren autenticación
        AuthenticationFilter filter = new AuthenticationFilter(requestMatcher, handlerExceptionResolver);
        filter.setAuthenticationManager(authentication -> authenticationProvider.authenticate(authentication));
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        });
        return filter;
    }

}


