package com.cursoandroid.pucfrotasdeescolar;

class Cliente extends Usuario {

    Cliente(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

    Cliente(String nome, String email, String senha) {
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
    }
}
