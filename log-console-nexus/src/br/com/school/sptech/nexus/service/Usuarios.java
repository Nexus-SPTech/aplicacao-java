package br.com.school.sptech.nexus.service;

public class Usuarios {
    private String usuario;
    private String email;
    private String senha;
    private String dataCadastro;

    public Usuarios() {
    }

    public Usuarios(String usuario, String email, String senha, String dataCadastro) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }


}
