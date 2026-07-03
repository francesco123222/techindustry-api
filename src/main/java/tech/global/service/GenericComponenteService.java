package tech.global.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tech.dto.ComponenteResponse;
import tech.global.model.IGenericBaseModel;

import java.io.Serializable;

public abstract class GenericComponenteService<R extends JpaRepository<E, T>, E extends IGenericBaseModel, T extends Serializable>
        implements IComponenteService<E, T> {

    @Autowired
    public R repository;

    // Cadastrar
    @Transactional(rollbackFor = Exception.class)
    public E incluir(E componente) {
        return repository.save(componente);
    }

    @Transactional(rollbackFor = Exception.class)
    public E atualizar(T id, E componente) {

        return repository.save(componente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(T id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado para exclusão com o ID: " + id);
        }

       repository.deleteById(id);
    }

}
