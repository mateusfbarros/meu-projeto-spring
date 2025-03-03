package com.example.meu_projeto_spring.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;

    public ReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public byte[] generateReport() throws Exception {
        // Carregar o template JRXML
        InputStream reportStream = new ClassPathResource("reports/report.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Buscar os dados do H2
        List<Map<String, Object>> data = jdbcTemplate.queryForList("SELECT id, nome FROM pessoa");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        String imagePath = new ClassPathResource("static/images/google-37.png").getURL().toString();

        // Parâmetros do relatório
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ImagePath", imagePath);


        // Preencher o relatório
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exportar para PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}