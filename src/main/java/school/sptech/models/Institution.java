package school.sptech.models;

public class Institution {
    private Integer codInstituicao;
    private String nomeDepartamento;
    private String distritoEstadual;
    private String municipio;
    private String regiaoMetropolitana;

    public Institution() {
    }

    public Institution(Integer codInstituicao, String nomeDepartamento, String distritoEstadual, String municipio, String regiaoMetropolitana) {
        this.codInstituicao = codInstituicao;
        this.nomeDepartamento = nomeDepartamento;
        this.distritoEstadual = distritoEstadual;
        this.municipio = municipio;
        this.regiaoMetropolitana = regiaoMetropolitana;
    }

    public Integer getCodInstituicao() {
        return codInstituicao;
    }

    public void setCodInstituicao(Integer codInstituicao) {
        this.codInstituicao = codInstituicao;
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
                Município: %s   '
                Região Metropolitana: %s
                """.formatted(codInstituicao, distritoEstadual, nomeDepartamento, municipio, regiaoMetropolitana);
    }
}
