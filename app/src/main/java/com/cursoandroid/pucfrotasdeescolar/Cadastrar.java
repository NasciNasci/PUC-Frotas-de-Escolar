package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cadastrar extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference motoristaDatabase = databaseReference.child("Motorista");
    private DatabaseReference clienteDatabase = databaseReference.child("Cliente");

    private RadioButton buttonMotorista;
    private RadioButton buttonAluno;
    private EditText textNome;
    private EditText textEmail;
    private EditText textSenha;
    private EditText textConfirmaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        buttonMotorista = findViewById(R.id.radio_button_motorista);
        buttonAluno = findViewById(R.id.radio_button_aluno);
        textNome = findViewById(R.id.edit_nome);
        textEmail = findViewById(R.id.edit_email);
        textSenha = findViewById(R.id.edit_senha);
        textConfirmaSenha = findViewById(R.id.edit_confirma_senha);
        Button cadastrar = findViewById(R.id.botao_entrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = textNome.getText().toString();
                String email = textEmail.getText().toString();
                String senha1 = textSenha.getText().toString();
                String senha2 = textConfirmaSenha.getText().toString();

                if ((!nome.equals("")) && (!senha1.equals("")) && (!senha2.equals(""))) {
                    if (verificaEmail(email)) {
                        if (senha1.equals(senha2)) {
                            if (buttonMotorista.isChecked()) {
                                Motorista motorista = new Motorista(nome, email, senha1);
                                motorista.setAcessos(0);
                                motorista.setInstituicoesAtendidas("");
                                motorista.setLocaisAtendidos("");
                                motorista.setTelefone("");
                                motorista.setDescricao("");
                                create(motorista, motoristaDatabase);
                            }
                            if (buttonAluno.isChecked()) {
                                Cliente cliente = new Cliente(nome, email, senha1);
                                create(cliente, clienteDatabase);
                            }
                            if (!buttonMotorista.isChecked() && !buttonAluno.isChecked()) {
                                Toast.makeText(getApplicationContext(), "Escolha o tipo da conta.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Senhas diferentes. Confirme novamente.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email inválido.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha os campos solicitados.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificaEmail(String email) {
        boolean resposta = false;
        if (!email.equals("") && email.contains("@") && (email.contains(".com") || email.contains(".br")))
            resposta = true;
        return resposta;
    }

    private void create(final Usuario usuario, final DatabaseReference databaseReference) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario.setId(Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", ""));
                boolean cadastrado = dataSnapshot.hasChild(usuario.getId());

                if (!cadastrado) {
                    databaseReference.child(usuario.getId()).setValue(usuario);
                    Toast.makeText(getApplicationContext(), "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();

                    if (usuario.getClass().equals(Motorista.class)) {
                        Intent intent = new Intent(getApplicationContext(), PrincipalMotorista.class);
                        intent.putExtra("email", usuario.getEmail());
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(getApplicationContext(), Listar_motoristas.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário já cadastrado anteriormente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}