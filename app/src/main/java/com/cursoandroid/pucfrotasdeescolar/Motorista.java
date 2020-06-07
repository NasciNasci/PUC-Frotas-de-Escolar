package com.cursoandroid.pucfrotasdeescolar;

import java.io.Serializable;

public class Motorista extends Usuario implements Serializable {

    private String descricao;
    private String instituicoesAtendidas;
    private String locaisAtendidos;
    private String telefone;
    private String urlPerfil;
    private String urlVan1;
    private String urlVan2;
    private String urlVan3;
    private String urlVan4;
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

    public void setUrlPerfil(String urlPerfil){ this.urlPerfil = urlPerfil; }

    public void setUrlVan1(String urlVan1){ this.urlVan1 = urlVan1; }

    public void setUrlVan2(String urlVan2){ this.urlVan2 = urlVan2; }

    public void setUrlVan3(String urlVan3) { this.urlVan3 = urlVan3; }

    public void setUrlVan4(String urlVan4) { this.urlVan4 = urlVan4; }

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
        return this.descricao;
    }

    public String getUrlPerfil(){ return this.urlPerfil; }

    public String getUrlVan1() { return this.urlVan1; }

    public String getUrlVan2() { return this.urlVan2; }

    public String getUrlVan3() { return this.urlVan3; }

    public String getUrlVan4() { return this.urlVan4; }
}
