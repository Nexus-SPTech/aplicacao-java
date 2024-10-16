package school.sptech.models;

public class StudentGrade {
    private Student student;
    private Discipline discipline;
    private Double nota;

    public StudentGrade() {
    }

    public Student getClassroom() {
        return student;
    }

    public void setClassroom(Student student) {
        this.student = student;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public StudentGrade(Student student, Discipline discipline, Double nota) {
        this.student = student;
        this.discipline = discipline;
        this.nota = nota;
    }

    public Student getAluno() {
        return student;
    }

    public void setAluno(Student student) {
        this.student = student;
    }

    public Discipline getDisciplina() {
        return discipline;
    }

    public void setDisciplina(Discipline discipline) {
        this.discipline = discipline;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return """
                Média: %.2f
                Disciplina: %s
                Nome do aluno: %s
                """.formatted(nota, discipline, student);

    }
}
