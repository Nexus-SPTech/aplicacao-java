package school.sptech.models;

public class Student {
    private Integer idAluno;
    // A fk instituição gera o nome da instituição do aluno
    // Precisa ser alterado para um atributo do tipo Institution
    private Institution institution;
    private String serie;
    private String periodo;
    private String genero;
    private Integer idade;

    public Student() {
    }

    public Student(Institution institution, String serie, String periodo, String genero, Integer idade) {
        this.institution = institution;
        this.serie = serie;
        this.periodo = periodo;
        this.genero = genero;
        this.idade = idade;
    }

    public Student(Integer idAluno, String serie, String periodo, String genero, Integer idade) {
        this.idAluno = idAluno;
        this.serie = serie;
        this.periodo = periodo;
        this.genero = genero;
        this.idade = idade;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
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
                Idade: %d""".formatted(idAluno, institution, serie, periodo, genero, idade);
    }
}
