package tech.dto;

import tech.model.User;
import tech.enums.UserRole;

public record UserResponse (
        Long id,
        String usuario,
        String cpf,
        UserRole role
) {

    public UserResponse(User user) {
        this(
                user.getId(),
                user.getUsuario(),
                user.getCpf(),
                user.getRole()
        );
    }
}