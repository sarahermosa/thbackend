package com.roshka.thbackend.config;

import com.roshka.thbackend.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(AbstractAuthenticationProcessingFilter.class);

    private static final String BEARER = "Bearer";

    private final HandlerExceptionResolver handlerExceptionResolver;

    public AuthenticationFilter(final RequestMatcher requestMatcher,
                                final HandlerExceptionResolver handlerExceptionResolver) {
        super(requestMatcher);
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        try {
            String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null) {
                throw new BusinessException("Bad credentials, missing auth token");
            }
            String token = StringUtils.removeStart(authHeader, BEARER).trim();

            final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
            return getAuthenticationManager().authenticate(auth);
        } catch (BusinessException e) {
            log.error("error", e);
            handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
