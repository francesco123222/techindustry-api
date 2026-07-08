package tech.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Monta um JSON customizado
        String jsonResponseBody = """
                {
                    "timestamp": "%s",
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "Acesso negado. Token JWT ausente, inválido ou expirado.",
                    "path": "%s"
                }
                """.formatted(LocalDateTime.now(), request.getRequestURI());

        response.getWriter().write(jsonResponseBody);
    }
}
