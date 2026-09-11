package tech.global.service;

import tech.global.model.IGenericBaseModel;

public interface IComponenteService<E extends IGenericBaseModel, T> {

    E buscarPorId(T id);
    void deletarComponente(T id);
}
