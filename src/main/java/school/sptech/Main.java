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
        // Gerando conexão com o banco de dados
        DBConnetionProvider dbConnectionProvider = new DBConnetionProvider();
        JdbcTemplate jdbcTemplate = dbConnectionProvider.getConnection();

        // Instanciando classe para leitura da base de dados
        ExcelService excelService = new ExcelService();

        // Instanciando classe de conexão com o S3
        S3Service s3Service = new S3Service();

        // Capturando a base de dados do Bucket S3
        InputStream excelArchive = s3Service.processS3Objects();

        // Lendo dados da base de dados e os colocando em listas ou Map
        Map<String, List<?>> readDatas = excelService.readExcel(excelArchive);
        List<Institution> institutions = (List<Institution>) readDatas.get("instituicoes");
        List<Student> students = (List<Student>) readDatas.get("alunos");

        // Criação das tabelas
        DBService dbService = new DBService();
        dbService.createTables(jdbcTemplate);

        // Persistência dos dados lidos
        dbService.insertDisciplines(jdbcTemplate);
        dbService.insertInstitutions(jdbcTemplate, institutions);
        dbService.insertStudents(jdbcTemplate, students);
        dbService.insertStudentsGrades(jdbcTemplate, readDatas);

        // Colocando a tag "LIDO" na base de dados que foi lida, após persistência de seus dados no banco
        s3Service.setTagReadExcel();

        // Log de sucesso
        System.out.println("A aplicação foi encerrada com Sucesso!");

        // Instanciando classe de envio de notificação para o cliente
        SlackClients slackClients = new SlackClients();
        slackClients.sendNotification("Olá, foram inseridos novos dados na nossa Dashboard, " +
                "navegue no nosso Site para se manter atualizado sobre as novas necessidades estudantis! " +
                "\nAcesse já: www.nexus.com.br");
    }
}
