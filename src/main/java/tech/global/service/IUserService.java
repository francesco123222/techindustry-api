package tech.global.service;

import tech.dto.user.UserRequest;
import tech.dto.user.UserResponse;
import tech.model.user.User;

public interface IUserService extends IUsersService<User, Long> {

    User cadastrarUsuario(User usuario);
    UserResponse atualizar(Long id, UserRequest request);
    void deletar(Long id);
}
