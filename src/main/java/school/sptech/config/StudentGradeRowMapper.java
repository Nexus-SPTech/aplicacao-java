package school.sptech.config;

import org.springframework.jdbc.core.RowMapper;
import school.sptech.models.Discipline;
import school.sptech.models.Student;
import school.sptech.models.StudentGrade;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentGradeRowMapper implements RowMapper<StudentGrade> {

    @Override
    public StudentGrade mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Student student = new Student(
                resultSet.getInt("idAluno"),
                resultSet.getString("serie"),
                resultSet.getString("periodo"),
                resultSet.getString("genero"),
                resultSet.getInt("idade")
        );

        Discipline discipline = new Discipline(
                resultSet.getInt("idDisciplina"),
                resultSet.getString("nome_disciplina")
        );

        StudentGrade studentGrade = new StudentGrade(student, discipline,
                resultSet.getDouble("nota"));

        return studentGrade;
    }
}
