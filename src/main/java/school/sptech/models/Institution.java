package school.sptech.models;

public class Institution {
    private Integer idInstituicao;
    private String nomeDepartamento;
    private String departamentoEstadual;
    private String municipio;
    private String regiaoMetropolitana;

    public Institution() {
    }

    public Institution(Integer idInstituicao, String departamentoEstadual, String nomeDepartamento) {
        this.idInstituicao = idInstituicao;
        this.departamentoEstadual = departamentoEstadual;
        this.nomeDepartamento = nomeDepartamento;
    }

    public Integer getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Integer idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getDepartamentoEstadual() {
        return departamentoEstadual;
    }

    public void setDepartamentoEstadual(String departamentoEstadual) {
        this.departamentoEstadual = departamentoEstadual;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getRegiaoMetropolitana() {
        return regiaoMetropolitana;
    }

    public void setRegiaoMetropolitana(String regiaoMetropolitana) {
        this.regiaoMetropolitana = regiaoMetropolitana;
    }

    @Override
    public String toString() {
        return """
                Id: %d
                Nome do departamento: %s
                Departamento estadual: %s
                Município: %s
                Região Metropolitana: %s
                """.formatted(idInstituicao, departamentoEstadual, nomeDepartamento, municipio, regiaoMetropolitana);
    }
}
