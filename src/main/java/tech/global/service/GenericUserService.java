package tech.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tech.global.model.IGenericBaseModel;

import java.io.Serializable;

public abstract class GenericUserService<R extends JpaRepository<E, T>, E extends IGenericBaseModel, T extends Serializable>
        implements IUsersService<E, T> {

    @Autowired
    public R repository;

    @Transactional(rollbackFor = Exception.class)
    public E cadastrarUsuario(E usuario) {
        return repository.save(usuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public E atualizar(T id, E  usuario) {

        return repository.save(usuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(T id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado para exclusão com o ID: " + id);
        }

        repository.deleteById(id);
    }

}
