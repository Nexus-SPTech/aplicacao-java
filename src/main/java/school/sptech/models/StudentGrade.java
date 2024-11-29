package school.sptech.models;

import java.util.HashMap;
import java.util.Map;

public class StudentGrade {
    private Student student;
    private Map<Integer, Double> notasAlunos;

    public StudentGrade() {
        this.notasAlunos = new HashMap<>();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<Integer, Double> getNotasAlunos() {
        return notasAlunos;
    }

    public void addNotasDisciplinas(Integer idDisciplina, Double nota) {
        notasAlunos.put(idDisciplina, nota);
    }


}
