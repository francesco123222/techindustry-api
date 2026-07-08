package tech.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tech.model.component.Componente;
import tech.repository.query.ComponenteRepositoryCustom;
import tech.utils.component.DatabaseUtils;

import java.util.List;

public class ComponenteRepositoryImpl implements ComponenteRepositoryCustom {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Componente> listarComponentesCustom() {
        return DatabaseUtils.listarComponentes(jdbcTemplate);
    }
}
