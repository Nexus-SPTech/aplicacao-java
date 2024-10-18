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
                            FOREIGN KEY (fkAluno) REFERENCES aluno(codAluno),
                            FOREIGN KEY (fkDisciplina) REFERENCES disciplina(idDisciplina)
                        )
                """);
    }

    public void insertDisciplines(JdbcTemplate jdbcTemplate) {
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

        for (Institution inst : institutions) {
            if (!institutionExists(jdbcTemplate, inst.getCodInstituicao())) {
                jdbcTemplate.update(sql, inst.getCodInstituicao(), inst.getDistritoEstadual(),
                        inst.getNomeDepartamento(), inst.getMunicipio(), inst.getRegiaoMetropolitana());
                System.out.println("Instituição inserida com sucesso");
            }
        }

        System.out.println("Instituições inseridas com sucesso!");
    }

    public void insertStudents(JdbcTemplate jdbcTemplate, List<Student> students) {
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

        System.out.println("Alunos inseridos com sucesso!");
    }

    public void insertStudentsGrades(JdbcTemplate jdbcTemplate, Map<String, List<?>> resultReadData) {
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