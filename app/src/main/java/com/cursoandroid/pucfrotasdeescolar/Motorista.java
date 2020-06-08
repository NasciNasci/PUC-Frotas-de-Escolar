package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;

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

    Motorista() {
        new Motorista("", "", "");
    }

    Motorista(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

    Motorista(String nome, String email, String senha) {
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
    }

    @NonNull
    public String toString() {
        return this.getNome() + "\n"
                + this.getEmail() + "\n"
                + "Instituições Atendidas: " + this.instituicoesAtendidas;
    }

    void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    void setInstituicoesAtendidas(String instituicoesAtendidas) {
        this.instituicoesAtendidas = instituicoesAtendidas;
    }

    void setLocaisAtendidos(String locaisAtendidos) {
        this.locaisAtendidos = locaisAtendidos;
    }

    void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    void setAcessos(int acessos) {
        this.acessos = acessos;
    }

    void setUrlPerfil(String urlPerfil) {
        this.urlPerfil = urlPerfil;
    }

    void setUrlVan1(String urlVan1) {
        this.urlVan1 = urlVan1;
    }

    void setUrlVan2(String urlVan2) {
        this.urlVan2 = urlVan2;
    }

    void setUrlVan3(String urlVan3) {
        this.urlVan3 = urlVan3;
    }

    void setUrlVan4(String urlVan4) {
        this.urlVan4 = urlVan4;
    }

    String getInstituicoesAtendidas() {
        return this.instituicoesAtendidas;
    }

    String getLocaisAtendidos() {
        return this.locaisAtendidos;
    }

    String getTelefone() {
        return this.telefone;
    }

    int getAcessos() {
        return this.acessos;
    }

    String getDescricao() {
        return this.descricao;
    }

    String getUrlPerfil() {
        return this.urlPerfil;
    }

    String getUrlVan1() {
        return this.urlVan1;
    }

    String getUrlVan2() {
        return this.urlVan2;
    }

    String getUrlVan3() {
        return this.urlVan3;
    }

    String getUrlVan4() {
        return this.urlVan4;
    }
}
