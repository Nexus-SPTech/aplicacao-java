package school.sptech.provider;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnetionProvider {

    private final DataSource dataSource;

    /* Aqui estão sendo definidas as credencias no banco de dados
    Estão sendo definidas em um construtor vazio, pois ele é executado assim que o programa é executado
    Assim ele configura todas as credencias do banco quando o programa é executado */
    public DBConnetionProvider() {
        // ********* LEMBRE-SE DE CONFIGURAR AS VARIAVEIS DE AMBIENTE *********
        String DBUSER = System.getenv("DB_USER");
        String DBPASSWORD = System.getenv("DB_PASSWORD");

        /* Para conectar com a EC2 crie uma variavel de ambiente com o nome "IP_EC2"
         coloque o ip da ec2 na variavel de ambiente /*/
        String IPEC2 = System.getenv("IP_EC2");

        String database = "dbnexus"; // É necessário criar o banco manualmente
        String ip = IPEC2 + ":3306"; // Para conectar com EC2 coloque a variavel de ambiente aqui

        // URL da conexão completa
        String url = "jdbc:mysql://" + ip + "/" + database;

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(DBUSER);
        basicDataSource.setPassword(DBPASSWORD);

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }

}
