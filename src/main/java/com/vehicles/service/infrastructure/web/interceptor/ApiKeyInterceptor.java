package com.vehicles.service.infrastructure.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private static final String API_KEY_HEADER = "x-api-key";

    private final String expectedApiKey;

    public ApiKeyInterceptor(@Value("${app.security.api-key}") String expectedApiKey) {
        this.expectedApiKey = expectedApiKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestApiKey = request.getHeader(API_KEY_HEADER);

        if (requestApiKey == null || !requestApiKey.equals(expectedApiKey)) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            String body = """
                    {
                        "timestamp": "%s",
                        "status": 401,
                        "error": "No autorizado",
                        "details": ["API Key inválida o ausente"]
                    }
                    """.formatted(LocalDateTime.now().toString());

            response.getWriter().write(body);
            return false;
        }

        return true;
    }
}
