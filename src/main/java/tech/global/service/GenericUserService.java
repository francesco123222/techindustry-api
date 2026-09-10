package tech.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tech.dto.user.UserResponse;
import tech.global.model.IGenericBaseModel;
import tech.handler.exception.ResourceNotFoundException;

import java.io.Serializable;

public abstract class GenericUserService<R extends JpaRepository<E, T>, E extends IGenericBaseModel, T extends Serializable>
        implements IUserService<E, T> {

    @Autowired
    public R repository;

    @Transactional(readOnly = true)
    public E buscarPorId(T id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário com ID " + id + " não existe no nosso banco de dados."
                ));
    }

    @Transactional(rollbackFor = Exception.class)
    public E atualizar(T id, E  usuario) {

        return repository.save(usuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletarUsuario(T id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado para exclusão com o ID: " + id);
        }

        repository.deleteById(id);
    }

}
