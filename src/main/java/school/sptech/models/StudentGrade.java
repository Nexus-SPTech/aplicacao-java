package school.sptech.models;

public class StudentGrade {
    private Student student;
    private Discipline discipline;
    private Double media;

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

    public StudentGrade(Student student, Discipline discipline, Double media) {
        this.student = student;
        this.discipline = discipline;
        this.media = media;
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

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return """
                Média: %.2f
                Disciplina: %s
                Nome do aluno: %s
                """.formatted(media, discipline, student);

    }
}
