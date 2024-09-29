package school.sptech;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class Principal {
    public static void main(String[] args) {

        DBProvedorConexao dbProvedorConexao = new DBProvedorConexao();
        JdbcTemplate connection = dbProvedorConexao.getConnection();

        connection.execute("""
                CREATE TABLE IF NOT EXISTS aluno (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    instituicao VARCHAR(255) NOT NULL,
                    serie VARCHAR(100) NOT NULL,
                    periodo VARCHAR(50) NOT NULL,
                    media DOUBLE
                    )
""");

        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)", "SPTech", "3 " +
                "Médio", "Noturno", 6.55);


        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)", "Objetivo",
                "9 Ano", "Diurno", 8.55);


        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)", "Anglo", "1 " +
                "Médio", "Vepertino", 4.66);


        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)", "Etec",
                "2 Médio", "Integral", 9.55);

        List<Aluno> alunosDoBanco = connection.query("SELECT * FROM aluno", new BeanPropertyRowMapper<>(Aluno.class));

        for (Aluno aluno : alunosDoBanco) {
            System.out.println(aluno);
        }
    }
}
