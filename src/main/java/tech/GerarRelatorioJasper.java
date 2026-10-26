package tech;

import net.sf.jasperreports.engine.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

public class GerarRelatorioJasper {

    public static void main(String[] args) {
        try {

            String url = System.getenv("DB_URL");
            String usuario = System.getenv("DB_USERNAME");
            String senha = System.getenv("DB_PASSWORD");

            InputStream inputStream = GerarRelatorioJasper.class.getResourceAsStream("/relatorios/people.jrxml");

            if (inputStream == null) {
                System.out.println("Erro: Arquivo do relatório não encontrado.");
                return;
            }

            System.out.println("Compilando o relatório...");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            System.out.println("Obtendo conexão direta com o banco de dados via JDBC...");
            try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {

                System.out.println("Conectado com sucesso ao banco: " + conexao.getMetaData().getURL());
                System.out.println("Preenchendo o relatório...");

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), conexao);

                System.out.println("Exportando para PDF...");
                JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/relatorios/relatorio_people.pdf");

                System.out.println("Sucesso! O relatório foi gerado em 'relatorio_people.pdf'.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao gerar o relatório!");
            e.printStackTrace();
        }
    }
}
