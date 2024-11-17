package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.provider.DBConnetionProvider;
import school.sptech.models.Institution;
import school.sptech.models.Student;
import school.sptech.service.DBService;
import school.sptech.service.ExcelService;
import school.sptech.service.S3Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DBConnetionProvider dbConnectionProvider = new DBConnetionProvider();
        JdbcTemplate jdbcTemplate = dbConnectionProvider.getConnection();

        ExcelService excelService = new ExcelService();
        // Instanciando classe de conexão com o S3
        S3Service s3Service = new S3Service();
        s3Service.getConnectionS3();

        InputStream excelArchive = s3Service.getExcelFileFromS3("nexus-group-bucket",
                "Base 001.xls");

        Map<String, List<?>> readDatas = excelService.readExcel(excelArchive);
        List<Institution> institutions = (List<Institution>) readDatas.get("instituicoes");
        List<Student> students = (List<Student>) readDatas.get("alunos");

        // Criação das tabelas
        DBService dbService = new DBService();
        dbService.createTables(jdbcTemplate);

        // Inserção de dados
        dbService.insertDisciplines(jdbcTemplate);
        dbService.insertInstitutions(jdbcTemplate, institutions);
        dbService.insertStudents(jdbcTemplate, students);
        dbService.insertStudentsGrades(jdbcTemplate, readDatas);
    }
}
