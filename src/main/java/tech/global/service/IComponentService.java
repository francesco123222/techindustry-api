package tech.global.service;

import tech.dto.component.ComponenteRequest;
import tech.dto.component.ComponenteResponse;
import tech.model.component.Componente;

public interface IComponentService extends IComponenteService<Componente, Long> {

    Componente incluir(Componente componente);
    ComponenteResponse atualizar(Long id, ComponenteRequest request);
    void deletar(Long id);
}
