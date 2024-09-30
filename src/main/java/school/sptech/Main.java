package school.sptech;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        DBConnetionProvider dbConnetionProvider = new DBConnetionProvider();
        JdbcTemplate connection = dbConnetionProvider.getConnection();

        // Criação das tabelas
        criarTabelas(connection);

        // Inserção de dados nas tabelas classroom e discipline
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
        connection.execute("DROP TABLE IF EXISTS discipline");
        connection.execute("DROP TABLE IF EXISTS classroom");

        // Criação da tabela classroom
        connection.execute("""
                CREATE TABLE classroom (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    instituicao VARCHAR(255) NOT NULL,
                    serie VARCHAR(100) NOT NULL,
                    periodo VARCHAR(50) NOT NULL
                )
        """);

        // Criação da tabela discipline
        connection.execute("""
                CREATE TABLE discipline (
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
                    FOREIGN KEY (fkAluno) REFERENCES classroom(id),
                    FOREIGN KEY (fkDisciplina) REFERENCES discipline(idDisciplina)
                )
        """);
    }

    private static void inserirAlunos(JdbcTemplate connection) {
        connection.update("INSERT INTO classroom (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "SPTech", "3º Médio", "Noturno");

        connection.update("INSERT INTO classroom (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "Objetivo", "9º Ano", "Diurno");

        connection.update("INSERT INTO classroom (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "Anglo", "1º Médio", "Vespertino");

        connection.update("INSERT INTO classroom (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "Etec", "2º Médio", "Integral");

        System.out.println("Alunos inseridos com sucesso!");
    }

    private static void inserirDisciplinas(JdbcTemplate connection) {
        connection.update("INSERT INTO discipline (nome) VALUES (?)", "História");
        connection.update("INSERT INTO discipline (nome) VALUES (?)", "Língua Portuguesa");
        connection.update("INSERT INTO discipline (nome) VALUES (?)", "Geografia");
        connection.update("INSERT INTO discipline (nome) VALUES (?)", "Matemática");

        System.out.println("Disciplinas inseridas com sucesso!");
    }

    private static void inserirNotasAluno(JdbcTemplate connection) {
        // Recuperar IDs dos alunos
        List<Classroom> classrooms = connection.query("SELECT * FROM classroom",
                new BeanPropertyRowMapper<>(Classroom.class));

        // Recuperar IDs das disciplinas
        List<Discipline> disciplines = connection.query("SELECT * FROM discipline",
                new BeanPropertyRowMapper<>(Discipline.class));

        // Inserir notas para cada classroom em cada discipline
        for (Classroom classroom : classrooms) {
            for (Discipline discipline : disciplines) {
                // Gerar uma nota aleatória entre 0 e 10
                double nota = Math.round(Math.random() * 10 * 100.0) / 100.0;

                connection.update("INSERT INTO notas_aluno (fkAluno, fkDisciplina, nota) VALUES (?, ?, ?)",
                        classroom.getId(), discipline.getIdDisciplina(), nota);

                System.out.printf("Inserida nota %.2f para o classroom ID %d na discipline ID %d%n",
                        nota, classroom.getId(), discipline.getIdDisciplina());
            }
        }

        System.out.println("Notas inseridas com sucesso!");
    }

    private static void exibirDados(JdbcTemplate connection) {
        // Exibir alunos
        System.out.println("\n--- Alunos ---");
        List<Classroom> classrooms = connection.query("SELECT * FROM classroom",
                new BeanPropertyRowMapper<>(Classroom.class));

        for (Classroom classroom : classrooms) {
            System.out.println(classroom);
        }

        // Exibir disciplinas
        System.out.println("\n--- Disciplinas ---");
        List<Discipline> disciplines = connection.query("SELECT * FROM discipline",
                new BeanPropertyRowMapper<>(Discipline.class));

        for (Discipline discipline : disciplines) {
            System.out.println(discipline);
        }

        // Exibir notas dos alunos
        System.out.println("\n--- Notas dos Alunos ---");
        List<ClassroomGrade> notas = connection.query("""
                        SELECT 
                            a.id AS aluno_id, a.instituicao, a.serie, a.periodo,
                            d.idDisciplina AS disciplina_id, d.nome,
                            n.nota
                        FROM notas_aluno n
                        JOIN classroom a ON n.fkAluno = a.id
                        JOIN discipline d ON n.fkDisciplina = d.idDisciplina
                        """,
                (rs, rowNum) -> {
                    Classroom classroom = new Classroom();
                    classroom.setId(rs.getInt("aluno_id"));
                    classroom.setInstituicao(rs.getString("instituicao"));
                    classroom.setSerie(rs.getString("serie"));
                    classroom.setPeriodo(rs.getString("periodo"));

                    Discipline discipline = new Discipline();
                    discipline.setIdDisciplina(rs.getInt("disciplina_id"));
                    discipline.setNome(rs.getString("nome"));

                    ClassroomGrade classroomGrade = new ClassroomGrade();
                    classroomGrade.setAluno(classroom);
                    classroomGrade.setDisciplina(discipline);
                    classroomGrade.setNota(rs.getDouble("nota"));

                    return classroomGrade;
                });

        for (ClassroomGrade classroomGrade : notas) {
            System.out.println(classroomGrade);
        }
    }
}
