package school.sptech;

public class Classroom {
    private Integer id;
    private String instituicao;
    private String serie;
    private String periodo;

    public Classroom() {
    }

    public Classroom(Integer id, String instituicao, String serie, String periodo) {
        this.id = id;
        this.instituicao = instituicao;
        this.serie = serie;
        this.periodo = periodo;
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


    @Override
    public String toString() {
        return "id: " + id + ", instituicao: " + instituicao  + ", serie: " + serie + ", periodo: " + periodo;
    }
}
