package tech.global.service;

import tech.global.model.IGenericBaseModel;

public interface IUserService<E extends IGenericBaseModel, T> {

    E buscarPorId(T id);
    void deletarUsuario(T id);
}
