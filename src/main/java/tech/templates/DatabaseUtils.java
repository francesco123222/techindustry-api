package tech.templates;

import org.springframework.jdbc.core.JdbcTemplate;
import tech.model.user.User;
import tech.model.user.enums.UserRole;

import java.util.List;

public class DatabaseUtils {
    public static List<String> listarTabelas(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'sch_techindustry'";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public static User carregarDadosModal(JdbcTemplate jdbcTemplate, Long id) {
        String sql = "SELECT id, usuario, cpf, role FROM sch_techindustry.tb_usuario WHERE id = ?";

            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setCpf(rs.getString("cpf"));
                String roleValue = rs.getString("role");
                if (roleValue != null) {
                    u.setRole(UserRole.valueOf(roleValue.toUpperCase()));
                }
                return u;
            }, id);
    }

    public static User salvarEdicaoModal(JdbcTemplate jdbcTemplate, User usuarioAtualizado) {
        String sql = "UPDATE sch_techindustry.tb_usuario SET usuario = ?, cpf = ?, role = ? WHERE id = ?";

        String roleStr = null;
        if (usuarioAtualizado.getRole() != null) {
            roleStr = usuarioAtualizado.getRole().name();
        }

        jdbcTemplate.update(sql,
                usuarioAtualizado.getUsuario(),
                usuarioAtualizado.getCpf(),
                roleStr,
                usuarioAtualizado.getId());

        return usuarioAtualizado;
    }

    public static User excluirUsuario(JdbcTemplate jdbcTemplate, Long id) {
        String sql = "SELECT id, usuario FROM sch_techindustry.tb_usuario WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setUsuario(rs.getString("usuario"));
            return u;
        }, id);
    }

    public static int executarExclusao(JdbcTemplate jdbcTemplate, Long id) {
        String sql = "DELETE FROM sch_techindustry.tb_usuario WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
