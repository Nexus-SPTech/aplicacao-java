package school.sptech;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassroomGradeRowMapper implements RowMapper<ClassroomGrade> {

    @Override
    public ClassroomGrade mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Classroom classroom = new Classroom(
                resultSet.getInt("idTurma"),
                resultSet.getString("fkInstituicao"),
                resultSet.getString("serie"),
                resultSet.getString("periodo")
        );

        Discipline discipline = new Discipline(
                resultSet.getInt("idDisciplina"),
                resultSet.getString("nome_disciplina")
        );

        ClassroomGrade classroomGrade = new ClassroomGrade(classroom, discipline,
                resultSet.getDouble("media"));

        return classroomGrade;
    }
}
