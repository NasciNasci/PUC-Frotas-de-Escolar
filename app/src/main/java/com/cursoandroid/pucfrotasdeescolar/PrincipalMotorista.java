package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PrincipalMotorista extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference motoristaDataBase = databaseReference.child("Motorista");
    //private StorageReference storageReference = ;
    private Intent intent;

    private CardView cardView;
    private ImageView imagemPerfil;

    private ImageView imagemVan1;
    private ImageView imagemVan2;
    private ImageView imagemVan3;
    private ImageView imagemVan4;

    private Motorista motorista = new Motorista();

    private EditText textDescricao;
    private EditText textBairro;
    private EditText textTelefone;
    private EditText textInstituicoes;
    private TextView numeroCliques;
    private TextView nome;
    private TextView textViewEmail;
    private Button buttonSalvar;
    private String email2;

    public Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_motorista);

        textDescricao = (EditText) findViewById(R.id.editText_descricao);
        textBairro = (EditText) findViewById(R.id.editText_bairros);
        textTelefone = (EditText) findViewById(R.id.editText_telefone);
        textInstituicoes = (EditText) findViewById(R.id.editText_instuicoes);
        numeroCliques = (TextView) findViewById(R.id.txt_quantidade_acessos);
        nome = (TextView) findViewById(R.id.txt_nome);
        textViewEmail = (TextView) findViewById(R.id.txt_email);
        buttonSalvar = (Button) findViewById(R.id.botao_salvar);

        imagemPerfil =(ImageView) findViewById(R.id.img_motorista);
        imagemVan1 = (ImageView) findViewById(R.id.imageView);
        imagemVan2 = (ImageView) findViewById(R.id.imageView2);
        imagemVan3 = (ImageView) findViewById(R.id.imageView3);
        imagemVan4 = (ImageView) findViewById(R.id.imageView4);


        intent = getIntent();
        email2 = intent.getStringExtra("email");
        imprimeTela();

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String descricao = textDescricao.getText().toString();
                final String bairro = textBairro.getText().toString();
                final String telefone = textTelefone.getText().toString();
                final String instituicoes = textInstituicoes.getText().toString();

                if((!descricao.equals("")) && (!bairro.equals("")) && (!telefone.equals("")) && (telefone.length() <= 9) && (!instituicoes.equals(""))){
                    motorista.setDescricao(descricao);
                    motorista.setLocaisAtendidos(bairro);
                    motorista.setInstituicoesAtendidas(instituicoes);
                    motorista.setTelefone(telefone);

                    motoristaDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String idUsuario = Base64.encodeToString(motorista.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                            motoristaDataBase.child(idUsuario).setValue(motorista);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                        Toast.makeText(getApplicationContext(), "Informações salvas com sucesso.", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(Cadastrar.this, PrincipalMotorista.class);
                       // intent.putExtra("email", email);
                        //startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(), "Preencha os campos solicitados.", Toast.LENGTH_SHORT).show();
                }

                escolherFoto();

            }
        });

    }
//Glide.with(MainActivity.this).load("gs://doe-amor.appspot.com/calendario_app.png").into(suaImageView);
    /*
    public class MainActivity extends AppCompatActivity {
    private ImageView proximaAcao;
    private ImageView galeria;
    private ImageView calendario;
    private ImageView contatos;



@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    proximaAcao = (ImageView) findViewById(R.id.proxima_acao_id);
    galeria = (ImageView) findViewById(R.id.galeria_id);
    calendario = (ImageView) findViewById(R.id.calendario_id);
    contatos = (ImageView) findViewById(R.id.contatos_id);


    Glide.with(MainActivity.this).load("gs://doe-amor.appspot.com/calendario_app.png");
     */
    private void imprimeTela(){
        motoristaDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idMotorista = Base64.encodeToString(email2.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                boolean motoristaCadastrado = dataSnapshot.hasChild(idMotorista);
                if(motoristaCadastrado){
                    motorista.setEmail(email2);
                    motorista.setNome(dataSnapshot.child(idMotorista).child("nome").getValue().toString());
                    motorista.setDescricao(dataSnapshot.child(idMotorista).child("descricao").getValue().toString());
                    motorista.setTelefone(dataSnapshot.child(idMotorista).child("telefone").getValue().toString());
                    motorista.setInstituicoesAtendidas(dataSnapshot.child(idMotorista).child("instituicoesAtendidas").getValue().toString());
                    motorista.setAcessos(Integer.parseInt(dataSnapshot.child(idMotorista).child("acessos").getValue().toString()));
                    motorista.setLocaisAtendidos(dataSnapshot.child(idMotorista).child("locaisAtendidos").getValue().toString());
                }else{
                    motorista = null;
                }

                if(motorista != null){
                    nome.setText(motorista.getNome());
                    textViewEmail.setText(motorista.getEmail());
                    textDescricao.setText(motorista.getDescricao());
                    textBairro.setText(motorista.getLocaisAtendidos());
                    textInstituicoes.setText(motorista.getInstituicoesAtendidas());
                    textTelefone.setText(motorista.getTelefone());
                    numeroCliques.setText(Integer.toString(motorista.getAcessos()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void escolherFoto(){
        intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            uri = data.getData();
            imagemPerfil.setImageURI(uri);
        }
    }
}
