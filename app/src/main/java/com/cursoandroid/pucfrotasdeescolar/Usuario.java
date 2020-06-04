package com.cursoandroid.pucfrotasdeescolar;

import android.util.Base64;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private boolean status;
    private boolean cadastrado;

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

    public void setCadastrado(boolean cadastrado) {
        this.cadastrado = cadastrado;
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

    public boolean getCadastrado() {
        return this.cadastrado;
    }

    public Usuario create(final Usuario usuario, final DatabaseReference databaseReference) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idUsuario = Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                cadastrado = dataSnapshot.hasChild(idUsuario);

                if (cadastrado) {
                    usuario.setStatus(false);
                } else {
                    databaseReference.child(idUsuario).setValue(usuario);
                    usuario.setStatus(true);
                }

                usuario.setId(idUsuario);
                usuario.setCadastrado(cadastrado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return usuario;
    }

    public Usuario login(final Usuario usuario, final DatabaseReference databaseReference) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idUsuario = Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                cadastrado = dataSnapshot.hasChild(idUsuario);

                if (!cadastrado) {
                    usuario.setStatus(false);
                } else {
                    if (usuario.getEmail().equals(dataSnapshot.child(idUsuario).child("email").getValue().toString()) && usuario.getSenha().equals(dataSnapshot.child(idUsuario).child("senha").getValue().toString())) {
                        usuario.setStatus(true);
                    }
                }

                usuario.setId(idUsuario);
                usuario.setCadastrado(cadastrado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return usuario;
    }
}
