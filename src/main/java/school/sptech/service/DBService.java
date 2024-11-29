package school.sptech.service;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.models.Institution;
import school.sptech.models.Student;
import school.sptech.models.StudentGrade;
import school.sptech.notification.SlackLogs;
import school.sptech.provider.DBConnetionProvider;

import java.util.List;
import java.util.Map;

public class DBService {
    DBConnetionProvider dbConnectionProvider = new DBConnetionProvider();
    JdbcTemplate jdbcTemplate = dbConnectionProvider.getConnection();
    SlackLogs slackLogs = new SlackLogs();

    public void createTables() {

        jdbcTemplate.execute("DROP TABLE IF EXISTS notas_aluno");
        jdbcTemplate.execute("DROP TABLE IF EXISTS disciplina");
        jdbcTemplate.execute("DROP TABLE IF EXISTS aluno");
        jdbcTemplate.execute("DROP TABLE IF EXISTS instituicao");
        System.out.println("Inicializando a criação das tabelas");

        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS instituicao (
                            codInstituicao INT PRIMARY KEY,
                            distrito_estadual VARCHAR(45) NOT NULL,
                            nome_departamento VARCHAR(45),
                            municipio VARCHAR(45),
                            regiao_metropolitana VARCHAR(45)
                        )
                """);
        System.out.println("Tabela Instituição criada com sucesso!");

        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS aluno (
                            codAluno INT PRIMARY KEY,
                            fkInstituicao INT,
                            serie VARCHAR(45) NOT NULL,
                            periodo VARCHAR(45),
                            genero VARCHAR(45),
                            idade INT,
                            FOREIGN KEY (fkInstituicao) REFERENCES instituicao(codInstituicao)
                        )
                """);
        System.out.println("Tabela Aluno criada com sucesso!");

        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS disciplina (
                            idDisciplina INT AUTO_INCREMENT PRIMARY KEY,
                            nome_disciplina VARCHAR(45) NOT NULL UNIQUE
                        )
                """);
        System.out.println("Tabela Disciplina criada com sucesso!");

        jdbcTemplate.execute("""
                        CREATE TABLE IF NOT EXISTS notas_aluno (
                            fkAluno INT,
                            fkDisciplina INT,
                            nota DOUBLE,
                            PRIMARY KEY (fkAluno, fkDisciplina),
                            FOREIGN KEY (fkAluno) REFERENCES aluno(codAluno),
                            FOREIGN KEY (fkDisciplina) REFERENCES disciplina(idDisciplina)
                        )
                """);
        System.out.println("Tabela Notas_Aluno criada com sucesso!");

        System.out.println("\nTodas as tabelas foram criadas com sucesso!");
        System.out.println("---------------------------");
    }

    public void insertDisciplines() {
        System.out.println("Inserindo disciplinas no banco...");
        String sql = "INSERT INTO disciplina (idDisciplina, nome_disciplina) VALUES (?, ?)";

        jdbcTemplate.update(sql, 1, "Português");
        jdbcTemplate.update(sql, 2, "Biologia");
        jdbcTemplate.update(sql, 3, "Física");
        jdbcTemplate.update(sql, 4, "Química");
        jdbcTemplate.update(sql, 5, "Matemática");
        jdbcTemplate.update(sql, 6, "Geografia");
        jdbcTemplate.update(sql, 7, "História");
        jdbcTemplate.update(sql, 8, "Filosofia");
        jdbcTemplate.update(sql, 9, "Sociologia");

//        List<Discipline> disciplines = jdbcTemplate.query("SELECT * FROM disciplina", new BeanPropertyRowMapper<>(Discipline.class));
//        System.out.println("Disciplinas inseridas com sucesso! \nDisciplinas inseridas: ");
//        disciplines.forEach(System.out::println);
    }

    public void insertInstitutions(List<Institution> institutions) {
        String sql = "INSERT IGNORE INTO instituicao (codInstituicao, distrito_estadual, nome_departamento, " +
                "municipio, regiao_metropolitana) VALUES (?, ?, ?, ?, ?)";

        System.out.println("""
                Inserindo instituições no banco...
                Verificando se a instituição existe no banco...
                """);

        if (institutions.isEmpty()) {
            System.out.println("Nenhuma instituição para inserir.");
        } else {
            for (Institution inst : institutions) {
                    try {
                        jdbcTemplate.update(sql, inst.getCodInstituicao(), inst.getDistritoEstadual(),
                                inst.getNomeDepartamento(), inst.getMunicipio(), inst.getRegiaoMetropolitana());
//                        System.out.println("Instituição inserida id: " + inst.getCodInstituicao());
                    } catch (DataAccessException e) {
                        System.err.println("Erro ao inserir a instituição: " + inst.getNomeDepartamento());
                        System.err.println("Mensagem de erro: " + e.getMessage());
                        slackLogs.sendNotification("Erro ao inserir a instituição: " + inst.getNomeDepartamento());
                        slackLogs.sendNotification("Mensagem de erro: " + e.getMessage());
                        e.printStackTrace(); // Imprime a stack trace do erro
                    }
            }
        }
        System.out.println("Dados das instituições inseridas com sucesso!");
    }

    public void insertStudents(List<Student> students) {
        System.out.println("Inserindo estudantes no banco...");
        // Recupera as instituições para obter os nomes

        String sql = "INSERT INTO aluno (codAluno, fkInstituicao, serie, periodo, genero, idade) VALUES (?, ?, ?, ?, " +
                "?, ?)";

        for (Student student : students) {
            jdbcTemplate.update(sql, student.getCodAluno(), student.getInstitution().getCodInstituicao(), student.getSerie(),
                    student.getPeriodo(), student.getGenero(), student.getIdade());
//            System.out.println("Aluno inserido com sucesso, Id do aluno: %d".formatted(student.getCodAluno()));
        }

        System.out.println("Dados dos estudantes inseridos com sucesso!");
    }

    public void insertStudentsGrades(Map<String, List<?>> resultReadData) {
        System.out.println("Inserindo notas dos estudantes no banco...");
        String sql = "INSERT INTO notas_aluno (fkAluno, fkDisciplina, nota) VALUES (?, ?, ?)";

        List<StudentGrade> grades = (List<StudentGrade>) resultReadData.get("notas");

        for (StudentGrade grade : grades) {
            Student student = grade.getStudent();

            for (Map.Entry<Integer, Double> entry : grade.getNotasAlunos().entrySet()) {
                Integer idDisciplina = entry.getKey();
                Double nota = entry.getValue();

                    if (nota != null) {
                        jdbcTemplate.update(sql, student.getCodAluno(), idDisciplina, nota);
//                        System.out.println("Nota inserida: Nota: %.2f Id Disciplina: %d".formatted(nota, idDisciplina) );
                    }

            }
        }
        System.out.println("Notas dos alunos inseridas com sucesso!");
        System.out.println("\nTodas os dados foram inseridos no banco com sucesso!");
        System.out.println("---------------------------");
    }


}