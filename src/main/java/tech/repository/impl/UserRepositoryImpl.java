package tech.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tech.model.user.User;
import tech.repository.query.UserRepositoryCustom;
import tech.utils.user.DatabaseUtils;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> listarUsuariosCustom() {
        return DatabaseUtils.listarUsuarios(jdbcTemplate);
    }
}
