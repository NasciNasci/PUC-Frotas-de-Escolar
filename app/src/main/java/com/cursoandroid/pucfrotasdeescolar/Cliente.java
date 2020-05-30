package com.cursoandroid.pucfrotasdeescolar;

public class Cliente extends Usuario {

    public Cliente() {
        new Cliente("", "", "");
    }

    public Cliente(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Cliente(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Cliente(String id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Cliente(Usuario usuario) {
        new Cliente(usuario.nome, usuario.email, usuario.senha);
    }
}
