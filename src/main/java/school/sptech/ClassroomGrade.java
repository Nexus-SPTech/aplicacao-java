package school.sptech;

public class ClassroomGrade {
    private Classroom classroom;
    private Discipline discipline;
    private Double nota;

    public ClassroomGrade() {
    }

    public ClassroomGrade(Classroom classroom, Discipline discipline, Double nota) {
        this.classroom = classroom;
        this.discipline = discipline;
        this.nota = nota;
    }

    public Classroom getAluno() {
        return classroom;
    }

    public void setAluno(Classroom classroom) {
        this.classroom = classroom;
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
        return "NotaAluno: " +
                "aluno= " + classroom +
                ", disciplina= " + discipline +
                ", nota= " + nota ;
    }
}
