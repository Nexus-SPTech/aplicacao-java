package school.sptech.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnetionProvider {

    private final DataSource dataSource;

    public DBConnetionProvider() {

        // Credenciais do banco de dados

        /* PARA CONEXÃO COM A EC2
        String username = "root";
        String password = "urubu100";
        String database = "nexus";
        String ip = "54.165.251.183";
        /*/

        // PARA CONEXÃO COM LOCAL
        String username = "INSIRA_SEU_NOME";
        String password = "INSIRA_SUA_SENHA";
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
