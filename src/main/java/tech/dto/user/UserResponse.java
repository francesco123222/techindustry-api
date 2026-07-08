package tech.dto.user;

import tech.model.user.User;
import tech.model.user.enums.UserRole;

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
