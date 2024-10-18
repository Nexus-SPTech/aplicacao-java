package school.sptech.models;

import java.util.HashMap;
import java.util.Map;

public class StudentGrade {
    private Student student;
    private Map<String, Double> notasDisciplinas;

    public StudentGrade() {
        this.notasDisciplinas = new HashMap<>();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<String, Double> getNotasDisciplinas() {
        return notasDisciplinas;
    }

    public void addNotasDisciplinas(String disciplina, Double nota) {
        notasDisciplinas.put(disciplina, nota);
    }

    //    @Override
//    public String toString() {
//        return """
//                Notas: %.2f
//                Disciplina: %s
//                ID do aluno: %s
//                """.formatted(notas, disciplines, student.getIdAluno());
//
//    }
}
