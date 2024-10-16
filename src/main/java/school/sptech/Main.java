package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.config.DBConnetionProvider;
import school.sptech.config.S3Provider;
import school.sptech.models.Institution;
import school.sptech.models.Student;
import school.sptech.service.DBService;
import school.sptech.service.ExcelService;
import school.sptech.service.S3Service;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Instancia o provedor de conexão DB
        DBConnetionProvider dbConnectionProvider = new DBConnetionProvider();
        JdbcTemplate jdbcTemplate = dbConnectionProvider.getConnection();
        DBService dbService = new DBService();

        // Instanciando classe que faz a leitura dos arquivos do excel
        ExcelService excelService = new ExcelService();
        // Instanciando classe de conexão com o S3
        S3Service s3Service = new S3Service();

        s3Service.getConnectionS3();

        // Variavel armazenando os dados lidos do excel
        Map<String, List<?>> readDatas = excelService.readExcel(s3Service.getExcelFileFromS3("nexus-group-bucket",
                "Microdados de Alunos - Ensino Medio PROVAO - 2023.xls"));
        List<Institution> institutions = (List<Institution>) readDatas.get("instituicoes");
        List<Student> students = (List<Student>) readDatas.get("alunos");

        // Criação das tabelas
        dbService.createTables(jdbcTemplate);

        // Inserção de dados
        dbService.insertDisciplines(jdbcTemplate);
        dbService.insertInstitutions(jdbcTemplate, institutions);
        dbService.insertStudents(jdbcTemplate, students);
        dbService.insertStudentsGrades(jdbcTemplate, readDatas);

    }

}
