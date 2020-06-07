package com.cursoandroid.pucfrotasdeescolar;

public class Cliente extends Usuario {

    public Cliente() {
        new Cliente("", "", "");
    }

    public Cliente(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

    public Cliente(String nome, String email, String senha) {
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
    }

}
