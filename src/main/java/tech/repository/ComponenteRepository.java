package tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.model.AreaComponent;
import tech.model.Componente;
import tech.model.TypeComponent;
import tech.repository.query.ComponenteRepositoryCustom;

import java.util.List;

@Repository
public interface ComponenteRepository extends JpaRepository<Componente, Long>, ComponenteRepositoryCustom {

    List<Componente> findByAreaComponent(AreaComponent areaComponent);

    List<Componente> findByTypeComponent(TypeComponent typeComponent);

    List<Componente> findByNomeContainingIgnoreCase(String nome);
}
