package com.roshka.thbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.roshka.thbackend.config.AuthEntryPointJwt;
import com.roshka.thbackend.config.AuthTokenFilter;
import com.roshka.thbackend.service.impl.UserDetailsServiceImpl;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/thbackend/auth/signin","/thbackend/auth/signup", "/thbackend/auth/forgot-password",
                                            "/thbackend/auth/reset-password").permitAll()
                                .requestMatchers(antMatcher(HttpMethod.PUT, "/thbackend/auth/restore-password")).authenticated()
                                .requestMatchers(antMatcher(HttpMethod.POST,"/thbackend/v1/postulante")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET,"/thbackend/v1/convocatoria")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET,"/thbackend/v1/ciudades")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET,"/thbackend/v1/tecnologia")).permitAll()
                                .requestMatchers("/v3/api-docs/**","/swagger-resources/**","/swagger-ui/**").permitAll()
                                .requestMatchers("/images/*").permitAll()
                                .requestMatchers("/cv/*").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}