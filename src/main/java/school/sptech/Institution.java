package school.sptech;

public class Institution {
    private Integer idInstituicao;
    private String nomeInstituicao;
    private String nomeDepartamento;

    public Institution() {
    }

    public Institution(Integer idInstituicao, String nomeInstituicao, String nomeDepartamento) {
        this.idInstituicao = idInstituicao;
        this.nomeInstituicao = nomeInstituicao;
        this.nomeDepartamento = nomeDepartamento;
    }

    public Integer getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Integer idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
}
