package tech.dto.token;

public record TokenResponse(
        Long id,
        String usuario,
        String token,
        String role
) {}