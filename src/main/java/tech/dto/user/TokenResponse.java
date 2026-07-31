package tech.dto.user;

public record TokenResponse(
        Long id,
        String usuario,
        String token,
        String role
) {}