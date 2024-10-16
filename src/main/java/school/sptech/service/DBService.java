package school.sptech.service;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.config.StudentGradeRowMapper;
import school.sptech.models.Discipline;
import school.sptech.models.Institution;
import school.sptech.models.Student;
import school.sptech.models.StudentGrade;

import java.util.List;

public class DBService {

    public void createTables(JdbcTemplate jdbcTemplate) {
        // Drop das tabelas existentes
        jdbcTemplate.execute("DROP TABLE IF EXISTS notas_aluno");
        jdbcTemplate.execute("DROP TABLE IF EXISTS disciplina");
        jdbcTemplate.execute("DROP TABLE IF EXISTS aluno");
        jdbcTemplate.execute("DROP TABLE IF EXISTS instituicao");

        // Criação nas tabelas no banco de dados MySQL:
        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS instituicao (
                            idInstituicao INT AUTO_INCREMENT PRIMARY KEY,
                            dep_estadual VARCHAR(45) NOT NULL,
                            nome_departamento VARCHAR(45),
                            municipio VARCHAR(45),
                            regiao_metropolitana VARCHAR(45)
                        )
                """);

        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS aluno (
                            idAluno INT AUTO_INCREMENT PRIMARY KEY,
                            fkInstituicao INT,
                            serie VARCHAR(45) NOT NULL,
                            periodo VARCHAR(45),
                            genero VARCHAR(45),
                            idade INT,
                            FOREIGN KEY (fkInstituicao) REFERENCES instituicao(idInstituicao)
                        )
                """);

        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS disciplina (
                            idDisciplina INT AUTO_INCREMENT PRIMARY KEY,
                            nome_disciplina VARCHAR(45) NOT NULL UNIQUE
                        )
                """);

        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS notas_aluno (
                            fkAluno INT,
                            fkDisciplina INT,
                            nota DOUBLE,
                            PRIMARY KEY (fkAluno, fkDisciplina),
                            FOREIGN KEY (fkAluno) REFERENCES aluno(idAluno),
                            FOREIGN KEY (fkDisciplina) REFERENCES disciplina(idDisciplina)
                        )
                """);
    }

    public void insertDisciplines(JdbcTemplate jdbcTemplate) {
        String sql = "INSERT INTO disciplina (nome_disciplina) VALUES (?)";

        jdbcTemplate.update(sql, "Matemática");
        jdbcTemplate.update(sql, "Português");
        jdbcTemplate.update(sql, "História");
        jdbcTemplate.update(sql, "Geografia");
        jdbcTemplate.update(sql, "Biologia");
        jdbcTemplate.update(sql, "Química");
        jdbcTemplate.update(sql, "Filosofia");
        jdbcTemplate.update(sql, "Sociologia");
        jdbcTemplate.update(sql, "Física");

        List<Discipline> disciplines = jdbcTemplate.query("SELECT * FROM disciplina", new BeanPropertyRowMapper<>(Discipline.class));
        System.out.println("Disciplinas inseridas com sucesso! \nDisciplinas inseridas: " );
        disciplines.forEach(System.out::println);
    }

    // Inserção de dados na tabela instituição
    public void insertInstitutions(JdbcTemplate jdbcTemplate) {
        // criando variavel com comando padrao de insert, para depois passarmos os dados a serem substituidos
        String sql = "INSERT INTO instituicao (dep_estadual, nome_departamento, municipio, regiao_metropolitana) VALUES (?, ?, ?, ?)";
        /* Explicação metodo UPDATE:
         Passamos como primeiro argumento o nome da variavel que iremos dar update(atualizar)
         Depois passamos os conteudos que serão atualizados na variavel
         Com isso as interrogações seram substituídas pelos argumentos passados
         O metodo update por fim executa o comando no banco
         /*/

        jdbcTemplate.update(sql, "SPTech", "Faculdade Privada","SP","Paulista");
        jdbcTemplate.update(sql, "Alberto Menrindes", "Escola estadual","MG","Centro");
        jdbcTemplate.update(sql, "USP", "Faculdade Publica","MG", "Interior");


        System.out.println("Instituições inseridas com sucesso!");
    }

    public void insertStudents(JdbcTemplate jdbcTemplate) {
        // Recupera as instituições para obter os nomes
        List<Institution> institutions = jdbcTemplate.query("SELECT * FROM instituicao", new BeanPropertyRowMapper<>(Institution.class));

        String sql = "INSERT INTO aluno (fkInstituicao, serie, periodo, genero, idade) VALUES (?, ?, ?, ?, ?)";

        for (Institution institution : institutions) {
            jdbcTemplate.update(sql, institution.getIdInstituicao(), "1º Ano", "Matutino","Não Binario","15");
            jdbcTemplate.update(sql, institution.getIdInstituicao(), "2º Ano", "Vespertino","Curioso","17");
        }

        System.out.println("Alunos inseridos com sucesso!");
    }


    public void insertClassroomGrades(JdbcTemplate jdbcTemplate) {
        // Recupera as alunos e disciplinas para obter os IDs
        List<Student> students = jdbcTemplate.query("SELECT * FROM aluno", new BeanPropertyRowMapper<>(Student.class));
        List<Discipline> disciplines = jdbcTemplate.query("SELECT * FROM disciplina", new BeanPropertyRowMapper<>(Discipline.class));

        String sql = "INSERT INTO notas_aluno (fkAluno, fkDisciplina, nota) VALUES (?, ?, ?)";


        /*/ Esses dois fors tem o objetivo de inserir uma nota aleatória
         Primeiramente é percorrido a lista de alunos no banco de dados
         Depois é percorrido a lista de disciplinas
         Pois cada aluno possui todas as disciplinas
         então para cada aluno, será necessário inserir notas de todas as disciplinas /*/
        for (Student student : students) {
            for (Discipline discipline : disciplines) {
                // Gera uma média aleatória entre 0 e 10
                double nota = Math.round(Math.random() * 10 * 100.0) / 100.0;

                jdbcTemplate.update(sql, student.getIdAluno(), discipline.getIdDisciplina(), nota);

                System.out.println("Inserida média %.2f para a aluno ID %d na disciplina ID %d".formatted(nota, student.getIdAluno(), discipline.getIdDisciplina()));
            }
        }

        System.out.println("Notas das alunos inseridas com sucesso!");
    }

    /*/ Esse metodo é responsavel por capturar os dados do banco e printar no console
     Ou seja, aqui acontece os selects /*/
    public void displayData(JdbcTemplate jdbcTemplate) {
        // Exibe Instituições
        System.out.println("\n--- Instituições ---");
        List<Institution> institutions = jdbcTemplate.query("SELECT * FROM instituicao", new BeanPropertyRowMapper<>(Institution.class));
        for (Institution institution : institutions) {
            System.out.println(institution);
        }

        // Exibe Alunos
        System.out.println("\n--- Alunos ---");
        List<Student> students = jdbcTemplate.query("""
                SELECT a.*, i.dep_estadual
                 AS fkInstituicao
                FROM aluno a
                JOIN instituicao i ON a.fkInstituicao = i.idInstituicao
                """, new BeanPropertyRowMapper<>(Student.class));
        for (Student student : students) {
            System.out.println(student);
        }

        // Exibe Disciplinas
        System.out.println("\n--- Disciplinas ---");
        List<Discipline> disciplines = jdbcTemplate.query("SELECT * FROM disciplina", new BeanPropertyRowMapper<>(Discipline.class));
        for (Discipline discipline : disciplines) {
            System.out.println(discipline);
        }

        // Exibe Notas dos Alunos
        System.out.println("\n--- Notas dos Alunos ---");
        List<StudentGrade> studentGrades = jdbcTemplate.query("""
                SELECT 
                    n.nota,
                    a.idAluno, a.serie, a.periodo,
                    d.idDisciplina, d.nome_disciplina,
                    i.dep_estadual
                     AS fkInstituicao
                FROM notas_aluno n
                JOIN aluno a ON n.fkAluno = a.idAluno
                JOIN disciplina d ON n.fkDisciplina = d.idDisciplina
                JOIN instituicao i ON a.fkInstituicao = i.idInstituicao
                """, new StudentGradeRowMapper());
        for (StudentGrade grade : studentGrades) {
            System.out.println(grade);
        }
    }

}
