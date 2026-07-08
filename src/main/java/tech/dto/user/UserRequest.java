package tech.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "O nome de usuário é obrigatório")
        @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
        String usuario,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(
                regexp = "(^\\d{11}$)|(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$)",
                message = "O CPF deve conter 11 números ou estar no formato 000.000.000-00"
        )
        String cpf,

        @NotNull(message = "A senha é obrigatória")
        @Size(min = 6, max = 100, message = "A sua senha deve ter entre 6 e 100 caracteres")
        String senha
) {}
