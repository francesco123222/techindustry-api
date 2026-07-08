package tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.model.component.enums.AreaComponent;
import tech.model.component.Componente;
import tech.model.component.enums.TypeComponent;
import tech.repository.query.ComponenteRepositoryCustom;

import java.util.List;

@Repository
public interface ComponenteRepository extends JpaRepository<Componente, Long>, ComponenteRepositoryCustom {

    List<Componente> findByAreaComponent(AreaComponent areaComponent);

    List<Componente> findByTypeComponent(TypeComponent typeComponent);

    List<Componente> findByNomeContainingIgnoreCase(String nome);
}
