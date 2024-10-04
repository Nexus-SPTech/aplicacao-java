package school.sptech;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Instancia o provedor de conexão
        DBConnetionProvider dbConnectionProvider = new DBConnetionProvider();
        JdbcTemplate jdbcTemplate = dbConnectionProvider.getConnection();

        // Criação das tabelas
        createTables(jdbcTemplate);

        // Inserção de dados
        insertInstitutions(jdbcTemplate);
        insertClassrooms(jdbcTemplate);
        insertDisciplines(jdbcTemplate);
        insertClassroomGrades(jdbcTemplate);

        // Exibição dos dados
        displayData(jdbcTemplate);
    }

    private static void createTables(JdbcTemplate jdbcTemplate) {
        // Drop das tabelas existentes
        jdbcTemplate.execute("DROP TABLE IF EXISTS notas_turma");
        jdbcTemplate.execute("DROP TABLE IF EXISTS disciplina");
        jdbcTemplate.execute("DROP TABLE IF EXISTS turma");
        jdbcTemplate.execute("DROP TABLE IF EXISTS instituicao");

        // Criação da tabela 'instituicao'
        jdbcTemplate.execute("""
                        CREATE TABLE instituicao (
                            idInstituicao INT AUTO_INCREMENT PRIMARY KEY,
                            nome_instituicao VARCHAR(45) NOT NULL,
                            nome_departamento VARCHAR(45)
                        )
                """);

        // Criação da tabela 'turma'
        jdbcTemplate.execute("""
                        CREATE TABLE turma (
                            idTurma INT AUTO_INCREMENT PRIMARY KEY,
                            fkInstituicao INT,
                            serie VARCHAR(45),
                            periodo VARCHAR(45),
                            FOREIGN KEY (fkInstituicao) REFERENCES instituicao(idInstituicao)
                        )
                """);

        // Criação da tabela 'disciplina'
        jdbcTemplate.execute("""
                        CREATE TABLE disciplina (
                            idDisciplina INT AUTO_INCREMENT PRIMARY KEY,
                            nome_disciplina VARCHAR(45)
                        )
                """);

        // Criação da tabela 'notas_turma'
        jdbcTemplate.execute("""
                        CREATE TABLE notas_turma (
                            fkTurma INT,
                            fkDisciplina INT,
                            media DOUBLE,
                            PRIMARY KEY (fkTurma, fkDisciplina),
                            FOREIGN KEY (fkTurma) REFERENCES turma(idTurma),
                            FOREIGN KEY (fkDisciplina) REFERENCES disciplina(idDisciplina)
                        )
                """);
    }

    // Inserção de dados na tabela instituição
    private static void insertInstitutions(JdbcTemplate jdbcTemplate) {
        // criando variavel com comando padrao de insert, para depois passarmos os dados a serem substituidos
        String sql = "INSERT INTO instituicao (nome_instituicao, nome_departamento) VALUES (?, ?)";

        /* Explicação metodo UPDATE:
         Passamos como primeiro argumento o nome da variavel que iremos dar update(atualizar)
         Depois passamos os conteudos que serão atualizados na variavel
         Com isso as interrogações seram substituídas pelos argumentos passados
         O metodo update por fim executa o comando no banco
         /*/

        jdbcTemplate.update(sql, "SPTech", "Faculdade Privada");
        jdbcTemplate.update(sql, "Alberto Menrindes", "Escola estadual");
        jdbcTemplate.update(sql, "USP", "Faculdade Publica");

        System.out.println("Instituições inseridas com sucesso!");
    }

    private static void insertClassrooms(JdbcTemplate jdbcTemplate) {
        // Recupera as instituições para obter os nomes
        List<Institution> institutions = jdbcTemplate.query("SELECT * FROM instituicao",
                new BeanPropertyRowMapper<>(Institution.class));

        String sql = "INSERT INTO turma (fkInstituicao, serie, periodo) VALUES (?, ?, ?)";

        for (Institution institution : institutions) {
            jdbcTemplate.update(sql, institution.getIdInstituicao(), "1º Ano", "Matutino");
            jdbcTemplate.update(sql, institution.getIdInstituicao(), "2º Ano", "Vespertino");
        }

        System.out.println("Turmas inseridas com sucesso!");
    }

    private static void insertDisciplines(JdbcTemplate jdbcTemplate) {
        String sql = "INSERT INTO disciplina (nome_disciplina) VALUES (?)";


        jdbcTemplate.update(sql, "Matemática");
        jdbcTemplate.update(sql, "Português");
        jdbcTemplate.update(sql, "História");
        jdbcTemplate.update(sql, "Geografia");

        System.out.println("Disciplinas inseridas com sucesso!");
    }

    private static void insertClassroomGrades(JdbcTemplate jdbcTemplate) {
        // Recupera as turmas e disciplinas para obter os IDs
        List<Classroom> classrooms = jdbcTemplate.query("SELECT * FROM turma",
                new BeanPropertyRowMapper<>(Classroom.class));
        List<Discipline> disciplines = jdbcTemplate.query("SELECT * FROM disciplina",
                new BeanPropertyRowMapper<>(Discipline.class));

        String sql = "INSERT INTO notas_turma (fkTurma, fkDisciplina, media) VALUES (?, ?, ?)";


        /*/ Esses dois fors tem o objetivo de inserir uma nota aleatória
         Primeiramente é percorrido a lista de turmas no banco de dados
         Depois é percorrido a lista de disciplinas
         Pois cada turma possui todas as disciplinas
         então para cada turma, será necessário inserir notas de todas as disciplinas /*/
        for (Classroom classroom : classrooms) {
            for (Discipline discipline : disciplines) {
                // Gera uma média aleatória entre 0 e 10
                double media = Math.round(Math.random() * 10 * 100.0) / 100.0;

                jdbcTemplate.update(sql, classroom.getIdTurma(), discipline.getIdDisciplina(), media);

                System.out.println("Inserida média %.2f para a turma ID %d na disciplina ID %d"
                        .formatted(media, classroom.getIdTurma(), discipline.getIdDisciplina())
                );
            }
        }

        System.out.println("Notas das turmas inseridas com sucesso!");
    }

    /*/ Esse metodo é responsavel por capturar os dados do banco e printar no console
     Ou seja, aqui acontece os selects /*/
    private static void displayData(JdbcTemplate jdbcTemplate) {
        // Exibe Instituições
        System.out.println("\n--- Instituições ---");
        List<Institution> institutions = jdbcTemplate.query("SELECT * FROM instituicao",
                new BeanPropertyRowMapper<>(Institution.class));
        for (Institution institution : institutions) {
            System.out.println(institution);
        }

        // Exibe Turmas
        System.out.println("\n--- Turmas ---");
        List<Classroom> classrooms = jdbcTemplate.query("""
                        SELECT t.*, i.nome_instituicao AS fkInstituicao
                        FROM turma t
                        JOIN instituicao i ON t.fkInstituicao = i.idInstituicao
                        """, new BeanPropertyRowMapper<>(Classroom.class));
        for (Classroom classroom : classrooms) {
            System.out.println(classroom);
        }

        // Exibe Disciplinas
        System.out.println("\n--- Disciplinas ---");
        List<Discipline> disciplines = jdbcTemplate.query("SELECT * FROM disciplina",
                new BeanPropertyRowMapper<>(Discipline.class));
        for (Discipline discipline : disciplines) {
            System.out.println(discipline);
        }

        // Exibe Notas das Turmas
        System.out.println("\n--- Notas das Turmas ---");
        List<ClassroomGrade> classroomGrades = jdbcTemplate.query("""
                        SELECT 
                            n.media,
                            t.idTurma, t.serie, t.periodo,
                            d.idDisciplina, d.nome_disciplina,
                            i.nome_instituicao AS fkInstituicao
                        FROM notas_turma n
                        JOIN turma t ON n.fkTurma = t.idTurma
                        JOIN disciplina d ON n.fkDisciplina = d.idDisciplina
                        JOIN instituicao i ON t.fkInstituicao = i.idInstituicao
                        """, new ClassroomGradeRowMapper());
        for (ClassroomGrade grade : classroomGrades) {
            System.out.println(grade);
        }
    }
}
