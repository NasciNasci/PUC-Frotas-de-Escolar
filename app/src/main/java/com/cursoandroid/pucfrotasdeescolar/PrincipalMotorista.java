package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PrincipalMotorista extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference motoristaDataBase = databaseReference.child("Motorista");

    private EditText textDescricao;
    private EditText textBairro;
    private EditText textTelefone;
    private EditText textInstituicoes;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_motorista);

        textDescricao = (EditText) findViewById(R.id.editText_descricao);
        textBairro = (EditText) findViewById(R.id.editText_bairros);
        textTelefone = (EditText) findViewById(R.id.editText_telefone);
        textInstituicoes = (EditText) findViewById(R.id.editText_instuicoes);
        buttonSalvar = (Button) findViewById(R.id.botao_salvar);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = textDescricao.getText().toString();
                String bairro = textBairro.getText().toString();
                String telefone = textTelefone.getText().toString();
                String instituicoes = textInstituicoes.getText().toString();
                //Motorista motorista = new Motorista(nome, email, senha1);
                if((!descricao.equals("")) && (!bairro.equals("")) && (!telefone.equals("")) && (!instituicoes.equals(""))){
                    motoristaDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String idUsuario = Base64.encodeToString(getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }//end if
            }
        });

    }
}
