package com.cursoandroid.pucfrotasdeescolar;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private boolean status;

    public Usuario() {
        new Usuario("", "", "");
    }

    public Usuario(String email, String senha){
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

    public Usuario(String email, String senha, boolean status){
        this.email = email;
        this.senha = senha;
        this.status = status;
    }

    public void setId(String id) { this.id = id; }

    public void setNome(String nome) { this.nome = nome; }

    public void setEmail(String email) { this.email = email; }

    public void setSenha(String senha) { this.senha = senha; }

    public void setStatus(boolean status) { this.status = status; }

    public String getId(){ return this.id; }

    public String getNome(){ return this.nome; }

    public String getEmail() { return email; }

    public String getSenha(){ return this.senha; }

    public boolean getStatus() { return this.status; }
}
