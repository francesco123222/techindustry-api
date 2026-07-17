package tech.utils.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.model.user.User;

import java.util.List;

public class DatabaseUtils {
    public static List<User> listarUsuarios(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT id, usuario, cpf, senha, " +
                "role " +
                "FROM sch_techindustry.tb_usuario ORDER by role, id";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public static User usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }
}
