package school.sptech;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnetionProvider {

    private final DataSource dataSource;

    public DBConnetionProvider() {

        // Credenciais do banco de dados
        String username = "root";
        String password = "urubu100";
        String database = "nexus";
        String ip = "54.165.251.183";

        // URL da conexão com a instancia da nuvem
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
