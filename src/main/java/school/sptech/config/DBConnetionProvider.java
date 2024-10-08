package school.sptech.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnetionProvider {

    private final DataSource dataSource;

    public DBConnetionProvider() {

        String DBUser = System.getenv("DBUser");
        String DBPassword = System.getenv("DBPassword");


        // Credenciais do banco de dados

        // PARA CONEXÃO COM A EC2
//        String username = "root";
//        String password = "urubu100";
//        String database = "nexus";
//        String ip = System.getenv("IPEC2");


        // PARA CONEXÃO COM LOCAL
        String username = DBUser;
        String password = DBPassword;
        String database = "dbnexus"; // É necessário criar o banco manualmente
        String ip = "localhost:3306";

        // URL da conexão completa
        String url = "jdbc:mysql://" + ip + "/" + database;

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }

}
