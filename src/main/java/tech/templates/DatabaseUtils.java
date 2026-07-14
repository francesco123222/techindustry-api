package tech.templates;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DatabaseUtils {
    public static List<String> listarTabelas(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'sch_techindustry'";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
