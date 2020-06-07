package com.cursoandroid.pucfrotasdeescolar;

import java.io.Serializable;

public class Motorista extends Usuario implements Serializable {

    private String descricao;
    private String instituicoesAtendidas;
    private String locaisAtendidos;
    private String telefone;
    private int acessos;

    public Motorista() {
        new Motorista("", "", "");
    }

    public Motorista(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

    public Motorista(String nome, String email, String senha) {
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
    }

    public Motorista(String id, String nome, String email, String senha) {
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
    }

    public String toString() {
        return this.getNome() + "\n"
                + this.getEmail() + "\n"
                + "Instituições Atendidas: " + this.instituicoesAtendidas;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setInstituicoesAtendidas(String instituicoesAtendidas) {
        this.instituicoesAtendidas = instituicoesAtendidas;
    }

    public void setLocaisAtendidos(String locaisAtendidos) {
        this.locaisAtendidos = locaisAtendidos;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setAcessos(int acessos) {
        this.acessos = acessos;
    }

    public String getInstituicoesAtendidas() {
        return this.instituicoesAtendidas;
    }

    public String getLocaisAtendidos() {
        return this.locaisAtendidos;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public int getAcessos() {
        return this.acessos;
    }

    public String getDescricao() {
        return descricao;
    }
}
