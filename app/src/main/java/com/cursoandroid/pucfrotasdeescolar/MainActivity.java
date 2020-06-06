package com.cursoandroid.pucfrotasdeescolar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference motoristaDatabase = databaseReference.child("Motorista");
    private DatabaseReference clienteDatabase = databaseReference.child("Cliente");
    private Motorista motorista;
    private RadioButton buttonMotorista;
    private RadioButton buttonAluno;
    private EditText emailUsuario;
    private EditText senhaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailUsuario = findViewById(R.id.edit_email);
        senhaUsuario = findViewById(R.id.edit_senha);
        buttonMotorista = findViewById(R.id.radio_button_motorista);
        buttonAluno = findViewById(R.id.radio_button_aluno);
        Button entrar = findViewById(R.id.botao_entrar);
        TextView cadastrar = findViewById(R.id.botao_criar_conta);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailUsuario.getText().toString();
                String senha = senhaUsuario.getText().toString();

                if ((!email.equals("")) && (!senha.equals(""))) {
                    if (verificaEmail(email)) {
                        if (buttonMotorista.isChecked()) {
                            //Motorista motorista = new Motorista(email, senha);
                            motorista = new Motorista(email, senha);
                            System.out.println("MAIN ACTIVIT:" + motorista.login(motorista, motoristaDatabase).getStatus());
                            if (motorista.login(motorista, motoristaDatabase).getStatus()) {
                                Toast.makeText(getApplicationContext(), "Login realizado com sucesso.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, PrincipalMotorista.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Dados incorretos.", Toast.LENGTH_SHORT).show();
                            }
                            System.out.println(motorista.getStatus() + " STATUS");
                        }
                        if (buttonAluno.isChecked()) {
                            Cliente cliente = new Cliente(email, senha);

                            if (cliente.login(cliente, clienteDatabase).getStatus()) {
                                Toast.makeText(getApplicationContext(), "Login realizado com sucesso.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, PrincipalCliente.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Dados incorretos.", Toast.LENGTH_SHORT).show();
                            }
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
                startActivity(new Intent(MainActivity.this,
                        Cadastrar.class));
            }
        });
    }

    private boolean verificaEmail(String email) {
        boolean resposta = false;
        if (!email.equals("") && email.contains("@") && (email.contains(".com") || email.contains(".br")))
            resposta = true;
        return resposta;
    }
}