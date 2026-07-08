package tech.utils.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import tech.model.user.User;

import java.util.List;

public class DatabaseUtils {
    public static List<User> listarUsuarios(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id, usuario, cpf, senha, " +
                "role " +
                "FROM sch_techindustry.tb_usuario ORDER by id";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}
