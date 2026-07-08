package tech.global.service;

import tech.global.model.IGenericBaseModel;

public interface IUsersService<E extends IGenericBaseModel, T> {

    E cadastrarUsuario(E usuario);
    E atualizar(T id, E usuario);
    void deletar(T id);
}
