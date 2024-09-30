package school.sptech;

public class Discipline {
    private Integer idDisciplina;
    private String nome;

    public Discipline() {
    }

    public Discipline(Integer idDisciplina, String nome) {
        this.idDisciplina = idDisciplina;
        this.nome = nome;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return  "Disciplina" +
                "idDisciplina= " + idDisciplina +
                ", nome= '" + nome + '\'';
    }
}
