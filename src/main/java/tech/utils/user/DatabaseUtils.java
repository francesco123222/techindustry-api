package tech.utils.user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.config.core.UserDetailsImpl;
import tech.model.User;

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

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getUser();
    }
}
