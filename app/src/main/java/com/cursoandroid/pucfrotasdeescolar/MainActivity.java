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

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference motorista = databaseReference.child("Motorista");
    private DatabaseReference cliente = databaseReference.child("Cliente");

    private RadioButton buttonMotorista;
    private RadioButton buttonAluno;
    private EditText emailUsuario;
    private EditText senhaUsuario;
    private Button entrar;
    private Button cadastrar;

    boolean criado = false;

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

                if(buttonMotorista.isChecked() || buttonAluno.isChecked()){

                    if( verificaEmail(email) && (!senha.equals(""))){

                        Usuario usuario = new Usuario(email, senha, false);

                        if(buttonMotorista.isChecked()){
                            //login(usuario, motorista);
                            if(login(usuario, motorista)) {
                                // Vai para a tela de motorista
                                //startActivity(new Intent(MainActivity.this, Motorista.class));
                            }// end if
                        }// end if
                        if(buttonAluno.isChecked()){
                            System.out.println("Entrou");
                            //login(usuario, cliente);
                            System.out.println(login(usuario, cliente) + " Status novo");
                            if(login(usuario, cliente)) {
                                System.out.println("Entrou4");
                                // Vai para a tela de cliente
                                startActivity(new Intent(MainActivity.this, PincipalCliente.class));
                            }// end if
                        }// end if
                    }else{
                        Toast.makeText(getApplicationContext(), "Preencha os campos solicitados.", Toast.LENGTH_SHORT).show();
                    }// end if
                }else {
                    Toast.makeText(getApplicationContext(), "Selecione uma opção.", Toast.LENGTH_SHORT).show();
                }// end if
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        Cadastrar.class));
            }
        });

    }

    private boolean verificaEmail(String email){
        boolean resposta = false;
        if(!email.equals("") && email.contains("@") && (email.contains(".com") || email.contains(".br")))
            resposta = true;
        return resposta;
    }// end verificaEmail()

    private boolean login(final Usuario usuario, final DatabaseReference databaseReference) {
        final String email = usuario.getEmail();
        final String senha = usuario.getSenha();
        criado = false;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idUsuario = Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                boolean usuarioCadatrado = dataSnapshot.hasChild(idUsuario);

                if (usuarioCadatrado) {
                    System.out.println("Entrou2");
                    usuario.setEmail(dataSnapshot.child(idUsuario).child("email").getValue().toString());
                    usuario.setSenha(dataSnapshot.child(idUsuario).child("senha").getValue().toString());

                    if (email.equals(usuario.getEmail()) && senha.equals(usuario.getSenha())) {
                        System.out.println("Entrou3");
                        usuario.setStatus(true);
                        criado = true;
                        System.out.println(criado + "NePossivel");
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                    }// end if
                }// end if
            }// end if

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println(criado+" Criado");

        return criado;
    }// end login()


}// end class
