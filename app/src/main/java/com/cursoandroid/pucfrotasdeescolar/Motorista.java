package com.cursoandroid.pucfrotasdeescolar;

public class Motorista extends Usuario {

    public Motorista() {
        new Motorista("", "", "");
    }

    public Motorista(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Motorista(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Motorista(String id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
