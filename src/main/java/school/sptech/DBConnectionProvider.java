package school.sptech;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnectionProvider {

    private final DataSource dataSource;

    public DBConnectionProvider() {
        // Altere com suas informações.
        String nomeUsuario = "nome_do_usuario";
        String senha = "senha_do_banco";
        String bancoUsado = "dbnexus";

        // Altere o endereço de conexão para se conectar com a nuvem.
        String url = "jdbc:mysql://localhost:3306/" + bancoUsado + "/" + nomeUsuario;

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(nomeUsuario);
        basicDataSource.setPassword(senha);

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }
}
