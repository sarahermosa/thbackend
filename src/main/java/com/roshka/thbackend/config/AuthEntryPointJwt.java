package com.roshka.thbackend.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        String errorMessage = authException.getMessage();
        Map<String, Object> errorResponse = new HashMap<>();
        if ("Bad credentials".equals(errorMessage)) {
            errorResponse.put("error", "Unauthorized");
            errorResponse.put("message", "Username or credentials invalid");
        } else if ("Full authentication is required to access this resource".equals(errorMessage)) {
            errorResponse.put("error", "Unauthorized");
            errorResponse.put("message", "Full authentication is required to access this resource");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Unauthorized");
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
