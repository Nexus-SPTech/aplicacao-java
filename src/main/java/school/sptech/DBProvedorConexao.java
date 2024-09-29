package school.sptech;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBProvedorConexao {

    private final DataSource dataSource;

    public DBProvedorConexao() {
        // Altere com suas informações.
        String nomeUsuario = "root";
        String senha = "ma140506";
        String bancoUsado = "dbnexus";

        // Altere o endereço de conexão para se conectar com a nuvem.
        String url = "jdbc:mysql://localhost:3306/" + bancoUsado + "/" + nomeUsuario;

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/" + bancoUsado);
        basicDataSource.setUsername(nomeUsuario);
        basicDataSource.setPassword(senha);

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }
}
