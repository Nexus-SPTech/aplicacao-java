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

        // Inserção de dados na tabela notas_turma
        inserirNotasTurma(connection);

        // Exibição dos dados inseridos
        exibirDados(connection);
    }

    private static void criarTabelas(JdbcTemplate connection) {
        // Drop das tabelas existentes
        connection.execute("DROP TABLE IF EXISTS instituicao");
        connection.execute("DROP TABLE IF EXISTS turma");
        connection.execute("DROP TABLE IF EXISTS disciplina");
        connection.execute("DROP TABLE IF EXISTS notas_turma");

        //Criação da tabela 'instituicao'
        connection.execute("""
                 CREATE TABLE IF NOT EXISTS instituicao (
                     idInstituicao INT AUTO_INCREMENT PRIMARY KEY,
                     nome_instituicao VARCHAR(45) NOT NULL,
                     nome_departamento VARCHAR(45)
                 );
                """);

        // Criação da tabela 'turma'
        connection.execute("""
                CREATE TABLE IF NOT EXISTS turma (
                    idTurma INT AUTO_INCREMENT PRIMARY KEY,
                    serie VARCHAR(45),
                    periodo VARCHAR(45),
                    fkInstituicao INT,
                    FOREIGN KEY (fkInstituicao) REFERENCES instituicao(idInstituicao)
                );
                """);

        //Criação da tabela 'disciplina'

        connection.execute("""
                CREATE TABLE IF NOT EXISTS disciplina (
                    idDisc INT AUTO_INCREMENT PRIMARY KEY,
                    nome_disciplina VARCHAR(45)
                );
                """);

        // Criação da tabela notas_turma
        connection.execute("""
                CREATE TABLE IF NOT EXISTS  notas_turma (
                    fkTurma INT,
                    fkDisc INT,
                    media VARCHAR(45),
                    PRIMARY KEY (fkTurma, fkDisc),
                    FOREIGN KEY (fkDisc) REFERENCES disciplina(idDisc)
                );
                """);
    }

    private static void inserirAlunos(JdbcTemplate connection) {
        connection.update("INSERT INTO turma (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "SPTech", "3º Médio", "Noturno");

        connection.update("INSERT INTO turma (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "Objetivo", "9º Ano", "Diurno");

        connection.update("INSERT INTO turma (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "Anglo", "1º Médio", "Vespertino");

        connection.update("INSERT INTO turma (instituicao, serie, periodo) VALUES (?, ?, ?)",
                "Etec", "2º Médio", "Integral");

        System.out.println("Alunos inseridos com sucesso!");
    }

    private static void inserirDisciplinas(JdbcTemplate connection) {
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "História");
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "Língua Portuguesa");
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "Geografia");
        connection.update("INSERT INTO disciplina (nome) VALUES (?)", "Matemática");

        System.out.println("Disciplinas inseridas com sucesso!");
    }

    private static void inserirNotasTurma(JdbcTemplate connection) {
        // Recuperar IDs dos alunos
        List<Classroom> classrooms = connection.query("SELECT * FROM turma",
                new BeanPropertyRowMapper<>(Classroom.class));

        // Recuperar IDs das disciplinas
        List<Discipline> disciplines = connection.query("SELECT * FROM disciplina",
                new BeanPropertyRowMapper<>(Discipline.class));

        // Inserir notas para cada classroom em cada discipline
        for (Classroom classroom : classrooms) {
            for (Discipline discipline : disciplines) {
                // Gerar uma nota aleatória entre 0 e 10
                double nota = Math.round(Math.random() * 10 * 100.0) / 100.0;

                connection.update("INSERT INTO notas_turma (fkAluno, fkDisciplina, nota) VALUES (?, ?, ?)",
                        classroom.getIdTurma(), discipline.getIdDisciplina(), nota);

                System.out.printf("Inserida nota %.2f para o classroom ID %d na discipline ID %d%n",
                        nota, classroom.getIdTurma(), discipline.getIdDisciplina());
            }
        }

        System.out.println("Notas inseridas com sucesso!");
    }

    private static void exibirDados(JdbcTemplate connection) {
        // Exibir alunos
        System.out.println("\n--- Alunos ---");
        List<Classroom> classrooms = connection.query("SELECT * FROM turma",
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
                        FROM notas_turma n
                        JOIN turma a ON n.fkAluno = a.id
                        JOIN disciplina d ON n.fkDisciplina = d.idDisciplina
                        """,
                (rs, rowNum) -> {
                    Classroom classroom = new Classroom();
                    classroom.setIdTurma(rs.getInt("aluno_id"));
                    classroom.setInstituicao(rs.getString("instituicao"));
                    classroom.setSerie(rs.getString("serie"));
                    classroom.setPeriodo(rs.getString("periodo"));

                    Discipline discipline = new Discipline();
                    discipline.setIdDisciplina(rs.getInt("disciplina_id"));
                    discipline.setNomeDisciplina(rs.getString("nome"));

                    ClassroomGrade classroomGrade = new ClassroomGrade();
                    classroomGrade.setAluno(classroom);
                    classroomGrade.setDisciplina(discipline);
                    classroomGrade.setMedia(rs.getDouble("nota"));

                    return classroomGrade;
                });

        for (ClassroomGrade classroomGrade : notas) {
            System.out.println(classroomGrade);
        }
    }
}
