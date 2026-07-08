package tech.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "O nome de usuário é obrigatório")
    String usuario,

    @NotBlank(message = "A senha é obrigatória")
    String senha
) {}
