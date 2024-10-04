package school.sptech;

public class Classroom {
    private Integer idTurma;
    private String fkInstituicao;
    private String serie;
    private String periodo;

    public Classroom() {
    }

    public Classroom(Integer idTurma, String fkInstituicao, String serie, String periodo) {
        this.idTurma = idTurma;
        this.fkInstituicao = fkInstituicao;
        this.serie = serie;
        this.periodo = periodo;
    }

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
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
        return "id: " + idTurma + ", instituicao: " + fkInstituicao + ", serie: " + serie + ", periodo: " + periodo;
    }
}
