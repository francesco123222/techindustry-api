package tech.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tech.global.model.IGenericBaseModel;
import tech.handler.exception.ResourceNotFoundException;

import java.io.Serializable;

public abstract class GenericComponenteService<R extends JpaRepository<E, T>, E extends IGenericBaseModel, T extends Serializable>
        implements IComponenteService<E, T> {

    @Autowired
    public R repository;

    @Transactional(rollbackFor = Exception.class)
    public E incluir(E componente) {
        return repository.save(componente);
    }

    @Transactional(readOnly = true)
    public E buscarPorId(T id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Componente com ID " + id + " não existe."
                ));
    }

    @Transactional(rollbackFor = Exception.class)
    public E atualizar(T id, E componente) {

        return repository.save(componente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletarComponente(T id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado para exclusão com o ID: " + id);
        }

       repository.deleteById(id);
    }

}
