package school.sptech;

public class Classroom {
    private Integer idTurma;
    private String instituicao;
    private String serie;
    private String periodo;

    public Classroom() {
    }

    public Classroom(Integer idTurma, String instituicao, String serie, String periodo) {
        this.idTurma = idTurma;
        this.instituicao = instituicao;
        this.serie = serie;
        this.periodo = periodo;
    }

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
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
        return "id: " + idTurma + ", instituicao: " + instituicao  + ", serie: " + serie + ", periodo: " + periodo;
    }
}
