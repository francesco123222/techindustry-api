package tech.global.service;

import tech.dto.ComponenteRequest;
import tech.dto.ComponenteResponse;
import tech.model.Componente;

public interface IComponentService extends IComponenteService<Componente, Long> {

    Componente incluir(Componente componente);
    ComponenteResponse atualizar(Long id, ComponenteRequest request);
    void deletar(Long id);
}
