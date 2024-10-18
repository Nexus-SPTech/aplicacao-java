package school.sptech.models;

public class Institution {
    private Integer idInstituicao;
    private String nomeDepartamento;
    private String distritoEstadual;
    private String municipio;
    private String regiaoMetropolitana;

    public Institution() {
    }

    public Institution(Integer idInstituicao, String nomeDepartamento, String distritoEstadual, String municipio, String regiaoMetropolitana) {
        this.idInstituicao = idInstituicao;
        this.nomeDepartamento = nomeDepartamento;
        this.distritoEstadual = distritoEstadual;
        this.municipio = municipio;
        this.regiaoMetropolitana = regiaoMetropolitana;
    }

    public Integer getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Integer idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getDistritoEstadual() {
        return distritoEstadual;
    }

    public void setDistritoEstadual(String distritoEstadual) {
        this.distritoEstadual = distritoEstadual;
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
                """.formatted(idInstituicao, distritoEstadual, nomeDepartamento, municipio, regiaoMetropolitana);
    }
}
