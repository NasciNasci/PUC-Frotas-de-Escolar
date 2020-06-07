package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference motoristaDatabase = databaseReference.child("Motorista");
    private DatabaseReference clienteDatabase = databaseReference.child("Cliente");

    private RadioButton buttonMotorista;
    private RadioButton buttonAluno;
    private EditText emailUsuario;
    private EditText senhaUsuario;
    private Button entrar;
    private TextView cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailUsuario = findViewById(R.id.edit_email);
        senhaUsuario = findViewById(R.id.edit_senha);
        buttonMotorista = findViewById(R.id.radio_button_motorista);
        buttonAluno = findViewById(R.id.radio_button_aluno);
        entrar = findViewById(R.id.botao_entrar);
        cadastrar = findViewById(R.id.botao_criar_conta);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailUsuario.getText().toString();
                String senha = senhaUsuario.getText().toString();

                if ((!email.equals("")) && (!senha.equals(""))) {
                    if (verificaEmail(email)) {
                        if (buttonMotorista.isChecked()) {
                            Motorista motorista = new Motorista(email, senha);

                            login(motorista, motoristaDatabase);
                        }
                        if (buttonAluno.isChecked()) {
                            Cliente cliente = new Cliente(email, senha);
                            login(cliente, clienteDatabase);
                        }
                        if (!buttonMotorista.isChecked() && !buttonAluno.isChecked()) {
                            Toast.makeText(getApplicationContext(), "Escolha o tipo da conta.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email inválido.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha os campos solicitados.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Cadastrar.class));
            }
        });

    }

    private boolean verificaEmail(String email) {
        boolean resposta = false;
        if (!email.equals("") && email.contains("@") && (email.contains(".com") || email.contains(".br")))
            resposta = true;
        return resposta;
    }

    private void login(final Usuario usuario, final DatabaseReference databaseReference) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario.setId(Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", ""));
                boolean cadastrado = dataSnapshot.hasChild(usuario.getId());

                if (cadastrado) {
                    if (usuario.getEmail().equals(dataSnapshot.child(usuario.getId()).child("email").getValue().toString()) && usuario.getSenha().equals(dataSnapshot.child(usuario.getId()).child("senha").getValue().toString())) {
                        Toast.makeText(getApplicationContext(), "Login realizado com sucesso.", Toast.LENGTH_SHORT).show();

                        if (usuario.getClass().equals(Motorista.class)) {
                            Intent intent = new Intent(getApplicationContext(), PrincipalMotorista.class);
                            usuario.setNome(dataSnapshot.child(usuario.getId()).child("nome").getValue().toString());
                            usuario.setEmail(dataSnapshot.child(usuario.getId()).child("email").getValue().toString());
                            usuario.setSenha(dataSnapshot.child(usuario.getId()).child("senha").getValue().toString());
                            Motorista motorista = (Motorista) usuario;

//                            intent.putExtra("email", usuario.getEmail());
//                            intent.putExtra("id", usuario.getId());
//                            intent.putExtra("nome", usuario.getNome());
//                            intent.putExtra("senha", usuario.getSenha());
//                            intent.putExtra("instituicoesAtendidas", motorista.getInstituicoesAtendidas());
//                            intent.putExtra("locaisAtendidos", motorista.getLocaisAtendidos());
//                            intent.putExtra("telefone", motorista.getTelefone());
//                            intent.putExtra("descricao", motorista.getDescricao());
//                            intent.putExtra("acessos", motorista.getAcessos());

                            intent.putExtra("motorista", motorista);
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(getApplicationContext(), Listar_motoristas.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
