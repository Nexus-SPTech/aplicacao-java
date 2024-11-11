package school.sptech.models;

public class Student {
    private Integer codAluno;
    private Institution institution;
    private String serie;
    private String periodo;
    private String genero;
    private Integer idade;

    public Student() {
    }

    public Student(Integer codAluno, Institution institution, String serie, String periodo, String genero,
                   Integer idade) {
        this.codAluno = codAluno;
        this.institution = institution;
        this.serie = serie;
        this.periodo = periodo;
        this.genero = genero;
        this.idade = idade;
    }

    public Student(Integer codAluno, String serie, String periodo, String genero, Integer idade) {
        this.codAluno = codAluno;
        this.serie = serie;
        this.periodo = periodo;
        this.genero = genero;
        this.idade = idade;
    }

    public Integer getCodAluno() {
        return codAluno;
    }

    public void setCodAluno(Integer codAluno) {
        this.codAluno = codAluno;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return """
                Id: %d
                Instituição: %s
                Serie: %s
                Periodo: %s
                Gênero: %s
                Idade: %d""".formatted(codAluno, institution, serie, periodo, genero, idade);
    }
}
