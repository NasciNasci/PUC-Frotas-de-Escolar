package com.cursoandroid.pucfrotasdeescolar;

public class Motorista extends Usuario {

    private String instituicoesAtendidas;
    private String locaisAtendidos;

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

    public void setInstituicoesAtendidas(String instituicoesAtendidas) {
        this.instituicoesAtendidas = instituicoesAtendidas;
    }

    public void setLocaisAtendidos(String locaisAtendidos) {
        this.locaisAtendidos = locaisAtendidos;
    }

    public String getInstituicoesAtendidas() {
        return this.instituicoesAtendidas;
    }

    public String getLocaisAtendidos() {
        return this.locaisAtendidos;
    }
}
