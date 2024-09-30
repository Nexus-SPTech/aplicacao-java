package school.sptech;

public class NotaAluno {
    private Aluno aluno;
    private Disciplina disciplina;
    private Double nota;

    public NotaAluno() {
    }

    public NotaAluno(Aluno aluno, Disciplina disciplina, Double nota) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
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
                "aluno= " + aluno +
                ", disciplina= " + disciplina +
                ", nota= " + nota ;
    }
}
