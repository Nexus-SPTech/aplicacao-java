package school.sptech.provider;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnetionProvider {

    private final DataSource dataSource;

    public DBConnetionProvider() {
        String DBUSER = System.getenv("DB_USER");
        String DBPASSWORD = System.getenv("DB_PASSWORD");

        String IPEC2 = System.getenv("IP_EC2");

        String database = "dbnexus";
        String ip = IPEC2 + ":3306";

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
