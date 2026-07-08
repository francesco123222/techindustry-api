package tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.model.user.User;
import tech.repository.query.UserRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    boolean existsByUsuario(String usuario);
    boolean existsByCpf(String cpf);
    Optional<User> findByUsuario(String usuario);
    Optional<User> findByCpf(String cpf);
}
