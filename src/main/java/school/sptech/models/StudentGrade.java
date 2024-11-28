package school.sptech.models;

import java.util.HashMap;
import java.util.Map;

public class StudentGrade {
    private Student student;
    private Map<Integer, Double> notasDisciplinas;

    public StudentGrade() {
        this.notasDisciplinas = new HashMap<>();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<Integer, Double> getNotasDisciplinas() {
        return notasDisciplinas;
    }

    public void addNotasDisciplinas(Integer idDisciplina, Double nota) {
        notasDisciplinas.put(idDisciplina, nota);
    }


}
