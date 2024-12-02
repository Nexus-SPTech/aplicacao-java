package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.notification.SlackClients;
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
        // Instanciando classe para leitura da base de dados
        ExcelService excelService = new ExcelService();

        // Lendo dados da base de dados e os colocando em listas ou Map
        Map<String, List<?>> readDatas = excelService.readExcel();
        List<Institution> institutions = (List<Institution>) readDatas.get("instituicoes");
        List<Student> students = (List<Student>) readDatas.get("alunos");

        // Criação das tabelas
        DBService dbService = new DBService();
        dbService.createTables();

        dbService.insertInstitutions(institutions);
        dbService.insertStudents(students);
        dbService.insertStudentsGrades(readDatas);

        // Log de sucesso
        System.out.println("A aplicação foi encerrada com Sucesso!");

    }
}
