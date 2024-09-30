package school.sptech;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Principal {
    public static void main(String[] args) {

        DBProvedorConexao dbProvedorConexao = new DBProvedorConexao();
        JdbcTemplate connection = dbProvedorConexao.getConnection();

        // Criação das tabelas
        criarTabelas(connection);

        // Inserção de dados nas tabelas aluno e disciplina
        inserirAlunos(connection);
        inserirDisciplinas(connection);

        // Inserção de dados na tabela notas_aluno
        inserirNotasAluno(connection);

        // Exibição dos dados inseridos
        exibirDados(connection);
    }

    private static void criarTabelas(JdbcTemplate connection) {
        // Drop das tabelas existentes
        connection.execute("DROP TABLE IF EXISTS notas_aluno");
        connection.execute("DROP TABLE IF EXISTS disciplina");
        connection.execute("DROP TABLE IF EXISTS aluno");

        // Criação da tabela aluno
        connection.execute("""
                CREATE TABLE aluno (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    instituicao VARCHAR(255) NOT NULL,
                    serie VARCHAR(100) NOT NULL,
                    periodo VARCHAR(50) NOT NULL,
                    media DOUBLE
                )
        """);

        // Criação da tabela disciplina
        connection.execute("""
                CREATE TABLE disciplina (
                    idDisciplina INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(45) UNIQUE NOT NULL 
                )
        """);

        // Criação da tabela notas_aluno
        connection.execute("""
                CREATE TABLE notas_aluno (
                    fkAluno INT,
                    fkDisciplina INT,
                    nota DOUBLE NOT NULL,
                    PRIMARY KEY (fkAluno, fkDisciplina),
                    FOREIGN KEY (fkAluno) REFERENCES aluno(id),
                    FOREIGN KEY (fkDisciplina) REFERENCES disciplina(idDisciplina)
                )
        """);
    }

    private static void inserirAlunos(JdbcTemplate connection) {
        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)",
                "SPTech", "3º Médio", "Noturno", 6.55);

        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)",
                "Objetivo", "9º Ano", "Diurno", 8.55);

        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)",
                "Anglo", "1º Médio", "Vespertino", 4.66);

        connection.update("INSERT INTO aluno (instituicao, serie, periodo, media) VALUES (?, ?, ?, ?)",
                "Etec", "2º Médio", "Integral", 9.55);

        System.out.println("Alunos inseridos com sucesso!");
    }

    private static void inserirDisciplinas(JdbcTemplate connection) {
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "História");
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "Língua Portuguesa");
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "Geografia");
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "Matemática");

        System.out.println("Disciplinas inseridas com sucesso!");
    }

    private static void inserirNotasAluno(JdbcTemplate connection) {
        // Recuperar IDs dos alunos
        List<Aluno> alunos = connection.query("SELECT * FROM aluno",
                new BeanPropertyRowMapper<>(Aluno.class));

        // Recuperar IDs das disciplinas
        List<Disciplina> disciplinas = connection.query("SELECT * FROM disciplina",
                new BeanPropertyRowMapper<>(Disciplina.class));

        // Inserir notas para cada aluno em cada disciplina
        for (Aluno aluno : alunos) {
            for (Disciplina disciplina : disciplinas) {
                // Gerar uma nota aleatória entre 0 e 10
                double nota = Math.round(Math.random() * 10 * 100.0) / 100.0;

                connection.update("INSERT INTO notas_aluno (fkAluno, fkDisciplina, nota) VALUES (?, ?, ?)",
                        aluno.getId(), disciplina.getIdDisciplina(), nota);

                System.out.printf("Inserida nota %.2f para o aluno ID %d na disciplina ID %d%n",
                        nota, aluno.getId(), disciplina.getIdDisciplina());
            }
        }

        System.out.println("Notas inseridas com sucesso!");
    }

    private static void exibirDados(JdbcTemplate connection) {
        // Exibir alunos
        System.out.println("\n--- Alunos ---");
        List<Aluno> alunos = connection.query("SELECT * FROM aluno",
                new BeanPropertyRowMapper<>(Aluno.class));

        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }

        // Exibir disciplinas
        System.out.println("\n--- Disciplinas ---");
        List<Disciplina> disciplinas = connection.query("SELECT * FROM disciplina",
                new BeanPropertyRowMapper<>(Disciplina.class));

        for (Disciplina disciplina : disciplinas) {
            System.out.println(disciplina);
        }

        // Exibir notas dos alunos
        System.out.println("\n--- Notas dos Alunos ---");
        List<NotaAluno> notas = connection.query("""
                        SELECT 
                            a.id AS aluno_id, a.instituicao, a.serie, a.periodo, a.media,
                            d.idDisciplina AS disciplina_id, d.nome,
                            n.nota
                        FROM notas_aluno n
                        JOIN aluno a ON n.fkAluno = a.id
                        JOIN disciplina d ON n.fkDisciplina = d.idDisciplina
                        """,
                (rs, rowNum) -> {
                    Aluno aluno = new Aluno();
                    aluno.setId(rs.getInt("aluno_id"));
                    aluno.setInstituicao(rs.getString("instituicao"));
                    aluno.setSerie(rs.getString("serie"));
                    aluno.setPeriodo(rs.getString("periodo"));
                    aluno.setMedia(rs.getDouble("media"));

                    Disciplina disciplina = new Disciplina();
                    disciplina.setIdDisciplina(rs.getInt("disciplina_id"));
                    disciplina.setNome(rs.getString("nome"));

                    NotaAluno notaAluno = new NotaAluno();
                    notaAluno.setAluno(aluno);
                    notaAluno.setDisciplina(disciplina);
                    notaAluno.setNota(rs.getDouble("nota"));

                    return notaAluno;
                });

        for (NotaAluno notaAluno : notas) {
            System.out.println(notaAluno);
        }
    }
}
