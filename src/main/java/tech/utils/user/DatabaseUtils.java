package tech.utils.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import tech.model.user.User;
import tech.model.user.enums.UserRole;

import java.util.List;

public class DatabaseUtils {
    public static List<User> listarUsuarios(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id, usuario, cpf, senha, " +
                "role " +
                "FROM sch_techindustry.tb_usuario ORDER by id";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}
