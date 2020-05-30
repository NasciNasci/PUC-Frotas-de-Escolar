package com.cursoandroid.pucfrotasdeescolar;

public class Motorista extends Usuario {

    private String instituicoesAtendidas;
    private String locaisAtendidos;
    private String telefone;
    private int acessos;

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

    public String toString() {
        return this.nome + "\n"
                + email + "\n"
                + "Instituições Atendidas: " + this.instituicoesAtendidas;
    }

    public void setInstituicoesAtendidas(String instituicoesAtendidas) {
        this.instituicoesAtendidas = instituicoesAtendidas;
    }

    public void setLocaisAtendidos(String locaisAtendidos) {
        this.locaisAtendidos = locaisAtendidos;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public void setAcessos(int acessos){
        this.acessos = acessos;
    }

    public String getInstituicoesAtendidas() {
        return this.instituicoesAtendidas;
    }

    public String getLocaisAtendidos() {
        return this.locaisAtendidos;
    }

    public String getTelefone(String telefone){
        return this.telefone;
    }

    public int getAcessos(){
        return this.acessos;
    }
}
