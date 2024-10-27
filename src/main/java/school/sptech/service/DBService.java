package school.sptech.service;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.models.Discipline;
import school.sptech.models.Institution;
import school.sptech.models.Student;
import school.sptech.models.StudentGrade;

import java.util.List;
import java.util.Map;

public class DBService {

    public void createTables(JdbcTemplate jdbcTemplate) {

        System.out.println("Inicializando a criação das tabelas");
        // Drop das tabelas existentes
        jdbcTemplate.execute("DROP TABLE IF EXISTS notas_aluno");
        jdbcTemplate.execute("DROP TABLE IF EXISTS disciplina");
        jdbcTemplate.execute("DROP TABLE IF EXISTS aluno");
        jdbcTemplate.execute("DROP TABLE IF EXISTS instituicao");

        // Criação nas tabelas no banco de dados MySQL:
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
    }

    public void insertDisciplines(JdbcTemplate jdbcTemplate) {
        System.out.println("Inserindo disciplinas no banco...");
        String sql = "INSERT INTO disciplina (nome_disciplina) VALUES (?)";

        /* Explicação metodo UPDATE:
         Passamos como primeiro argumento o nome da variavel que iremos dar update(atualizar)
         Depois passamos os conteudos que serão atualizados na variavel
         Com isso as interrogações seram substituídas pelos argumentos passados
         O metodo update por fim executa o comando no banco
         /*/
        jdbcTemplate.update(sql, "Português");
        jdbcTemplate.update(sql, "Biologia");
        jdbcTemplate.update(sql, "Física");
        jdbcTemplate.update(sql, "Química");
        jdbcTemplate.update(sql, "Matemática");
        jdbcTemplate.update(sql, "Geografia");
        jdbcTemplate.update(sql, "História");
        jdbcTemplate.update(sql, "Filosofia");
        jdbcTemplate.update(sql, "Sociologia");

        List<Discipline> disciplines = jdbcTemplate.query("SELECT * FROM disciplina", new BeanPropertyRowMapper<>(Discipline.class));
        System.out.println("Disciplinas inseridas com sucesso! \nDisciplinas inseridas: ");
        disciplines.forEach(System.out::println);
    }

    public boolean institutionExists(JdbcTemplate jdbcTemplate, Integer codInst) {
        String sql = "SELECT COUNT(*) FROM instituicao WHERE codInstituicao = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, codInst);
        return count != null && count > 0;
    }

    public void insertInstitutions(JdbcTemplate jdbcTemplate, List<Institution> institutions) {
        // criando variavel com comando padrao de insert, para depois passarmos os dados a serem substituidos
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
                if (!institutionExists(jdbcTemplate, inst.getCodInstituicao())) {
                    try {
                        jdbcTemplate.update(sql, inst.getCodInstituicao(), inst.getDistritoEstadual(),
                                inst.getNomeDepartamento(), inst.getMunicipio(), inst.getRegiaoMetropolitana());
                        System.out.println("Instituição inserida: " + inst.getNomeDepartamento());
                    } catch (Exception e) {
                        System.err.println("Erro ao inserir a instituição: " + inst.getNomeDepartamento());
                        e.printStackTrace(); // Imprime a stack trace do erro
                    }
                } else {
                    System.out.println("Instituição já existe: " + inst.getNomeDepartamento());
                }
            }
        }

        System.out.println("Dados das instituições inseridos com sucesso!");

        System.out.println("Dados das instituições inseridos com sucesso!");
    }

    public void insertStudents(JdbcTemplate jdbcTemplate, List<Student> students) {
        System.out.println("Inserindo estudantes no banco...");
        // Recupera as instituições para obter os nomes

        String sql = "INSERT INTO aluno (codAluno, fkInstituicao, serie, periodo, genero, idade) VALUES (?, ?, ?, ?, " +
                "?, ?)";

        String select = "SELECT * FROM instituicao WHERE codInstituicao = ?";
        for (Student student : students) {
            List<Institution> institutions = jdbcTemplate.query("SELECT * FROM instituicao " +
                            "WHERE codInstituicao = ?",
                    new BeanPropertyRowMapper<>(Institution.class),
                    student.getInstitution().getCodInstituicao()
            );
            jdbcTemplate.update(sql, student.getCodAluno(), institutions.get(0).getCodInstituicao(), student.getSerie(),
                    student.getPeriodo(), student.getGenero(), student.getIdade());

        }

        System.out.println("Dados dos estudantes inseridos com sucesso!");
    }

    public void insertStudentsGrades(JdbcTemplate jdbcTemplate, Map<String, List<?>> resultReadData) {
        System.out.println("Inserindo notas dos estudantes no banco...");
        String sql = "INSERT INTO notas_aluno (fkAluno, fkDisciplina, nota) VALUES (?, ?, ?)";

        // Captura os StudentGrade do Map resultReadData
        List<StudentGrade> grades = (List<StudentGrade>) resultReadData.get("notas");

        // Itera sobre cada StudentGrade
        for (StudentGrade grade : grades) {
            // Obtém o estudante associado
            Student student = grade.getStudent();

            // Itera sobre a HashMap StudentGrade que contém a (disciplina e nota)
            for (Map.Entry<String, Double> entry : grade.getNotasDisciplinas().entrySet()) {
                // Obtem a key da HashMap, sendo ela o mesmo nome da disciplina que está inserido no banco
                String disciplinaNome = entry.getKey();
                // Obtém a nota com relação a matéria (que é a key da HashMap)
                Double nota = entry.getValue();

                // Consulta o ID da disciplina com base no nome
                List<Discipline> disciplines = jdbcTemplate.query(
                        "SELECT * FROM disciplina WHERE nome_disciplina = ?",
                        new BeanPropertyRowMapper<>(Discipline.class),
                        disciplinaNome
                );

                // Verifica se encontrou a disciplina
                if (!disciplines.isEmpty()) {
                    Discipline disciplina = disciplines.get(0); // Assume que a consulta retornou uma disciplina válida

                    // Insere a nota do aluno para a disciplina
                    jdbcTemplate.update(sql, student.getCodAluno(), disciplina.getIdDisciplina(), nota);

                } else {
                    System.out.println("Disciplina não encontrada: " + disciplinaNome);
                }
            }
        }

        System.out.println("Notas das alunos inseridas com sucesso!");
    }

}