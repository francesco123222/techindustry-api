package tech.global.service;

import tech.global.model.IGenericBaseModel;

public interface IComponenteService<E extends IGenericBaseModel, T> {

    E incluir(E componente);
    E atualizar(T id, E componente);
    void deletar(T id);
}
