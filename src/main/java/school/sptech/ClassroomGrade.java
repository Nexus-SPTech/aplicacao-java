package school.sptech;

public class ClassroomGrade {
    private Classroom classroom;
    private Discipline discipline;
    private Double media;

    public ClassroomGrade() {
    }

    public ClassroomGrade(Classroom classroom, Discipline discipline, Double media) {
        this.classroom = classroom;
        this.discipline = discipline;
        this.media = media;
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

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "ClassroomGrade{" +
                "media=" + media +
                ", discipline=" + discipline +
                ", classroom=" + classroom +
                '}';
    }
}
