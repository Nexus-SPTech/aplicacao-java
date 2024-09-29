package school.sptech;

public class Aluno {
    private Integer id;
    private String instituicao;
    private String serie;
    private String periodo;
    private Double media;

    public Aluno() {
    }

    public Aluno(Integer id, String instituicao, String serie, String periodo, Double media) {
        this.id = id;
        this.instituicao = instituicao;
        this.serie = serie;
        this.periodo = periodo;
        this.media = media;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "id: " + id + ", instituicao: " + instituicao  + ", serie: " + serie + ", periodo: " + periodo +
                ", media: " + media;
    }
}
