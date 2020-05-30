package com.cursoandroid.pucfrotasdeescolar;

import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Usuario {
    protected String id;
    protected String nome;
    protected String email;
    protected String senha;
    protected boolean status;
    protected boolean usuarioJaCadastrado;

    public Usuario() {
        new Usuario("", "", "");
    }

    public Usuario(String email, String senha) {
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

    public Usuario(String email, String senha, boolean status) {
        this.email = email;
        this.senha = senha;
        this.status = status;
    }

    public Usuario(Usuario usuario) {
        new Usuario(usuario.nome, usuario.email, usuario.senha);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

    public boolean getStatus() {
        return this.status;
    }

    public boolean create(final Usuario usuario, final DatabaseReference databaseReference) {
        usuarioJaCadastrado = false;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idUsuario = Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                setId(idUsuario);
                usuarioJaCadastrado = dataSnapshot.hasChild(idUsuario);

                if (usuarioJaCadastrado) {
                    usuarioJaCadastrado = true;
                } else {
                    databaseReference.child(idUsuario).setValue(usuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return usuarioJaCadastrado;
    }
}
