package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private DatabaseReference motorista = databaseReference.child("Motorista");
    private DatabaseReference cliente = databaseReference.child("Cliente");

    private RadioButton buttonMotorista;
    private RadioButton buttonAluno;
    private EditText textNome;
    private EditText textEmail;
    private EditText textSenha;
    private EditText textConfirmaSenha;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        buttonMotorista = findViewById(R.id.radio_button_motorista);
        buttonAluno =  findViewById(R.id.radio_button_aluno);
        textNome = findViewById(R.id.edit_nome);
        textEmail = findViewById(R.id.edit_email);
        textSenha = findViewById(R.id.edit_senha);
        textConfirmaSenha = findViewById(R.id.edit_confirma_senha);
        cadastrar = findViewById(R.id.botao_entrar);


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = textNome.getText().toString();
                String email = textEmail.getText().toString();
                String senha1 = textSenha.getText().toString();
                String senha2 = textConfirmaSenha.getText().toString();

                //buttonAluno.isChecked();
                //buttonMotorista.isChecked();

                //buttonMotorista.

//                if(buttonAluno.isChecked() || buttonMotorista.isChecked()){

                if((!nome.equals("")) && verificaEmail(email) && (!senha1.equals("")) && (!senha2.equals(""))){

                    if(senha1.equals(senha2)){

                        Usuario usuario = new Usuario(nome, email, senha1);
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setSenha(senha1);
                        if(buttonMotorista.isChecked()){
                            cadastrarUsuario(usuario, motorista);
                        }
                        if(buttonAluno.isChecked()){
                            cadastrarUsuario(usuario,cliente);
                        }// end if
                        Toast.makeText(getApplicationContext(), "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Senhas diferentes. Confirme novamente.", Toast.LENGTH_SHORT).show();
                    }// end if
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha os campos solicitados.", Toast.LENGTH_SHORT).show();
                }// end if
                //}else{
                //  Toast.makeText(getApplicationContext(), "Selecione uma opção.", Toast.LENGTH_SHORT).show();
                //}// end if

                // Vai para a tela de desejada.
                //startActivity(new Intent(cadastrar.this, MainActivity.class));
            }
        });

    }

    private boolean verificaEmail(String email){
        boolean resposta = false;
        if(!email.equals("") && email.contains("@") && (email.contains(".com") || email.contains(".br")))
            resposta = true;
        return resposta;
    }

    private void cadastrarUsuario(final Usuario usuario, final DatabaseReference databaseReference){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idUsuario = Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                usuario.setId(Integer.parseInt(idUsuario));
                boolean usuarioJaCadastrado = dataSnapshot.hasChild(idUsuario);

                if(usuarioJaCadastrado)
                    Toast.makeText(getApplicationContext(), "Usuário já cadastrado anteriormente.", Toast.LENGTH_SHORT).show();
                else
                    databaseReference.child(idUsuario).setValue(usuario);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }// end cadastrarUsuario()
}
