package tech.utils.component;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import tech.model.component.Componente;

import java.util.List;

public class DatabaseUtils {
    public static List<Componente> listarComponentes(JdbcTemplate jdbcTemplate) {
        // Mapeamos a coluna 'area' para virar 'areaComponent' e 'tipo' para virar 'typeComponent'
        String sql = "SELECT id, nome, descricao, preco, " +
                "area AS \"areaComponent\", " +
                "tipo AS \"typeComponent\" " +
                "FROM sch_techindustry.tb_componente ORDER BY id";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Componente.class));
    }
}
