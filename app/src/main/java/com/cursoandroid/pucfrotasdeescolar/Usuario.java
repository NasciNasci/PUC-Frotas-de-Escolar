package com.cursoandroid.pucfrotasdeescolar;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
        new Usuario("", "", "");
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String email, String senha, boolean status) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(Usuario usuario) {
        new Usuario(usuario.nome, usuario.email, usuario.senha);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }
}
