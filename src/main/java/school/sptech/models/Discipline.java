package school.sptech.models;

public class Discipline {
    private Integer idDisciplina;
    private String nomeDisciplina;

    public Discipline() {
    }

    public Discipline(Integer idDisciplina, String nomeDisciplina) {
        this.idDisciplina = idDisciplina;
        this.nomeDisciplina = nomeDisciplina;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    @Override
    public String toString() {
        return """
                Id: %d
                Disciplina: %s
                """.formatted(idDisciplina, nomeDisciplina);

    }
}
