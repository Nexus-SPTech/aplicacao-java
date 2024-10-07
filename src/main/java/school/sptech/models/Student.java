package school.sptech.models;

public class Student {
    private Integer idAluno;
    // A fk instituição gera o nome da instituição do aluno
    // Precisa ser alterado para um atributo do tipo Institution
    private String fkInstituicao;
    private String serie;
    private String periodo;

    public Student() {
    }

    public Student(Integer idAluno, String fkInstituicao, String serie, String periodo) {
        this.idAluno = idAluno;
        this.fkInstituicao = fkInstituicao;
        this.serie = serie;
        this.periodo = periodo;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public String getFkInstituicao() {
        return fkInstituicao;
    }

    public void setFkInstituicao(String fkInstituicao) {
        this.fkInstituicao = fkInstituicao;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }


    @Override
    public String toString() {
        return """
                Id: %d
                Instituicao: %s
                Serie: %s
                Periodo: %s""".formatted(idAluno, fkInstituicao, serie, periodo);
    }
}
