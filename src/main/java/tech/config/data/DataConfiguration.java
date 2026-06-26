package tech.config.data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Configuration
public class DataConfiguration {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/techdatabase");
        dataSource.setUsername("postgres");
        dataSource.setPassword("12345");

        // Executa o log exatamente no momento em que o Bean é criado pelo Spring
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            String databaseUrl = metaData.getURL();
            String driverName = metaData.getDriverName();
            String dbVersion = metaData.getDatabaseProductVersion();
            boolean autoCommit = conn.getAutoCommit();
            int isolationLevel = conn.getTransactionIsolation();

            // Print formatado usando o próprio objeto local 'dataSource'
            System.out.println("Database JDBC URL [Connecting through datasource '" + databaseUrl + "']");
            System.out.println("Database driver: " + driverName);
            System.out.println("Database version: " + dbVersion);
            System.out.println("Autocommit mode: " + autoCommit);
            System.out.println("Isolation level: " + translateIsolationLevel(isolationLevel));

        } catch (Exception e) {
            // Log de aviso caso o banco de dados esteja desligado ao iniciar a aplicação
            System.err.println("Erro ao ler metadados do banco: " + e.getMessage());
            System.err.println("Database driver: undefined/unknown");
            System.err.println("Database version: undefined/unknown");
            System.err.println("Autocommit mode: undefined/unknown");
            System.err.println("Isolation level: undefined/unknown");
        }

        return dataSource;
    }

    // Método auxiliar movido para dentro da classe de configuração
    private String translateIsolationLevel(int level) {
        return switch (level) {
            case Connection.TRANSACTION_READ_UNCOMMITTED -> "READ_UNCOMMITTED";
            case Connection.TRANSACTION_READ_COMMITTED -> "READ_COMMITTED";
            case Connection.TRANSACTION_REPEATABLE_READ -> "REPEATABLE_READ";
            case Connection.TRANSACTION_SERIALIZABLE -> "SERIALIZABLE";
            default -> "undefined/unknown";
        };
    }
}
