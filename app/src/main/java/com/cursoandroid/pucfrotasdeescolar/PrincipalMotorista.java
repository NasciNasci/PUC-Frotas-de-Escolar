package com.cursoandroid.pucfrotasdeescolar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrincipalMotorista extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference motoristaDataBase = databaseReference.child("Motorista");
    private ArrayAdapter<Motorista> adapter;

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

                if((!descricao.equals("")) && (!bairro.equals("")) && (!telefone.equals("")) && (!instituicoes.equals(""))){

                }//end if
            }
        });

    }
}
