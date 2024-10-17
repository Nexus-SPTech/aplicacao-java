package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.config.DBConnetionProvider;
import school.sptech.config.S3Provider;
import school.sptech.service.DBService;
import school.sptech.service.S3Service;

public class Main {
    public static void main(String[] args) {
        // Instancia o provedor de conexão
        DBConnetionProvider dbConnectionProvider = new DBConnetionProvider();
        JdbcTemplate jdbcTemplate = dbConnectionProvider.getConnection();
        DBService dbService = new DBService();

        // Criação das tabelas
        dbService.createTables(jdbcTemplate);

        // Inserção de dados
        dbService.insertInstitutions(jdbcTemplate);
        dbService.insertStudents(jdbcTemplate);
        dbService.insertDisciplines(jdbcTemplate);
        dbService.insertStudentsGrades(jdbcTemplate);

        // Exibição dos dados, comente se não quiser exibir
        dbService.displayData(jdbcTemplate);

        // Instanciando classe de conexão com o S3
        S3Provider s3Provider = new S3Provider();
        s3Provider.getS3Client();

        S3Service s3Service = new S3Service();
        s3Service.getConnectionS3();

    }

}
