package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class TelaMotoristaEscolhido extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference motoristaDataBase = databaseReference.child("Motorista");

    private Intent intent;

    private ImageView imagemPerfil;

    private ImageView imagemVan1;
    private ImageView imagemVan2;
    private ImageView imagemVan3;
    private ImageView imagemVan4;

    private Motorista motorista = new Motorista();

    private TextView textDescricao;
    private TextView textBairro;
    private TextView textTelefone;
    private TextView textInstituicoes;
    private TextView numeroCliques;
    private TextView nome;
    private TextView textViewEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_motorista_escolhido);

        textDescricao = findViewById(R.id.editText_descricao);
        textBairro = findViewById(R.id.editText_bairros);
        textTelefone = findViewById(R.id.editText_telefone);
        textInstituicoes = findViewById(R.id.editText_instuicoes);
        numeroCliques = findViewById(R.id.txt_quantidade_acessos);
        nome = findViewById(R.id.txt_nome);
        textViewEmail = findViewById(R.id.txt_email);

        imagemPerfil = findViewById(R.id.img_motorista);
        imagemVan1 = findViewById(R.id.imageView);
        imagemVan2 = findViewById(R.id.imageView2);
        imagemVan3 = findViewById(R.id.imageView3);
        imagemVan4 = findViewById(R.id.imageView4);

        intent = getIntent();
        motorista = (Motorista) intent.getSerializableExtra("motorista");

        imprimeTela();

//        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

    }

    private void imprimeTela() {

        motoristaDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idMotorista = Base64.encodeToString(motorista.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                boolean motoristaCadastrado = dataSnapshot.hasChild(idMotorista);
                if (motoristaCadastrado) {

                    textDescricao.setText(dataSnapshot.child(idMotorista).child("descricao").getValue().toString());
                    textBairro.setText(dataSnapshot.child(idMotorista).child("locaisAtendidos").getValue().toString());
                    textInstituicoes.setText(dataSnapshot.child(idMotorista).child("instituicoesAtendidas").getValue().toString());
                    textTelefone.setText(dataSnapshot.child(idMotorista).child("telefone").getValue().toString());
                    numeroCliques.setText(motorista.getAcessos() + "");

                    if(!motorista.getUrlPerfil().equals("")) {
                        Picasso.get().load(motorista.getUrlPerfil()).into(imagemPerfil);
                    }
                    if (!motorista.getUrlVan1().equals("")) {
                        Picasso.get().load(motorista.getUrlVan1()).into(imagemVan1);
                    }
                    if (!motorista.getUrlVan2().equals("")) {
                        Picasso.get().load(motorista.getUrlVan2()).into(imagemVan2);
                    }
                    if (!motorista.getUrlVan3().equals("")) {
                        Picasso.get().load(motorista.getUrlVan3()).into(imagemVan3);
                    }
                    if (!motorista.getUrlVan4().equals("")) {
                        Picasso.get().load(motorista.getUrlVan4()).into(imagemVan4);
                    }

                    motoristaDataBase.child(motorista.getId()).removeValue();
                    motoristaDataBase.child(motorista.getId()).setValue(motorista);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (motorista != null) {
            nome.setText(motorista.getNome());
            textViewEmail.setText(motorista.getEmail());
        }
    }


}
