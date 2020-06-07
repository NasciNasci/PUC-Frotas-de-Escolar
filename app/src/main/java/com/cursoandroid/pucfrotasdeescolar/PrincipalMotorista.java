package com.cursoandroid.pucfrotasdeescolar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PrincipalMotorista extends AppCompatActivity {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    public Uri uri;
    public int numeroImagem;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference motoristaDataBase = databaseReference.child("Motorista");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Intent intent;
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
    private String urlPerfil;
    private String urlVan1;
    private String urlVan2;
    private String urlVan3;
    private String urlVan4;


    private Map<String, Uri> uriMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_motorista);
        initializeViews();

        uriMap = new HashMap<>();
        urlPerfil = "";
        urlVan1 = "";
        urlVan2 = "";
        urlVan3 = "";
        urlVan4 = "";

        intent = getIntent();
        motorista = (Motorista) intent.getSerializableExtra("motorista");

        imprimeTela();

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        imagemPerfil.setOnClickListener(v -> {
            numeroImagem = 1;
            startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
        });

        imagemVan1.setOnClickListener(v -> {
            numeroImagem = 2;
            startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
        });

        imagemVan2.setOnClickListener(v -> {
            numeroImagem = 3;
            startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
        });

        imagemVan3.setOnClickListener(v -> {
            numeroImagem = 4;
            startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
        });

        imagemVan4.setOnClickListener(v -> {
            numeroImagem = 5;
            startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
        });

        buttonSalvar.setOnClickListener(v -> {
            if(uriMap.size() > 0) {
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                uploadAllImages(url -> {
                    getDataAndUpdateMotorista();
                    atualizar(motorista, motoristaDataBase);
                    progressDialog.dismiss();
                });
            }
        });
    }

    private void uploadAllImages(OnCompleteUpload completeUpload) {
        uploadImage("perfil", url -> {
            urlPerfil = url;
            uploadImage("van1", url1 -> {
                urlVan1 = url1;
                uploadImage("van2", url2 -> {
                    urlVan2 = url2;
                    uploadImage("van3", url3 -> {
                        urlVan3 = url3;
                        uploadImage("van4", url4 -> {
                            urlVan4 = url4;
                            completeUpload.onComplete("");
                        });
                    });
                });
            });
        });
    }

    private void getDataAndUpdateMotorista() {
        String descricao = textDescricao.getText().toString();
        String bairro = textBairro.getText().toString();
        String telefone = textTelefone.getText().toString();
        String instituicoes = textInstituicoes.getText().toString();
        motorista.setDescricao(descricao);
        motorista.setLocaisAtendidos(bairro);
        motorista.setTelefone(telefone);
        motorista.setInstituicoesAtendidas(instituicoes);
        checkingsAndUpdatesMotorista();
    }

    private void checkingsAndUpdatesMotorista() {
        if (urlPerfil != null)
            motorista.setUrlPerfil(urlPerfil);

        if (urlVan1 != null)
            motorista.setUrlVan1(urlVan1);

        if (urlVan2 != null)
            motorista.setUrlVan2(urlVan2);

        if (urlVan3 != null)
            motorista.setUrlVan3(urlVan3);

        if (urlVan4 != null)
            motorista.setUrlVan4(urlVan4);
    }

    private void initializeViews() {
        textViewEmail = findViewById(R.id.txt_email);
        textDescricao = findViewById(R.id.editText_descricao);
        textBairro = findViewById(R.id.editText_bairros);
        textTelefone = findViewById(R.id.editText_telefone);
        textInstituicoes = findViewById(R.id.editText_instuicoes);
        numeroCliques = findViewById(R.id.txt_quantidade_acessos);
        nome = findViewById(R.id.txt_nome);
        buttonSalvar = findViewById(R.id.botao_salvar);
        imagemPerfil = findViewById(R.id.img_motorista);
        imagemVan1 = findViewById(R.id.imageView);
        imagemVan2 = findViewById(R.id.imageView2);
        imagemVan3 = findViewById(R.id.imageView3);
        imagemVan4 = findViewById(R.id.imageView4);
    }

    private void imprimeTela() {

        motoristaDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idMotorista = Base64.encodeToString(motorista.getEmail().getBytes(), Base64.DEFAULT).replaceAll("([\\n\\r])", "");
                boolean motoristaCadastrado = dataSnapshot.hasChild(idMotorista);
                if (motoristaCadastrado) {
                    textDescricao.setText(Objects.requireNonNull(dataSnapshot.child(idMotorista).child("descricao").getValue()).toString());
                    textBairro.setText(Objects.requireNonNull(dataSnapshot.child(idMotorista).child("locaisAtendidos").getValue()).toString());
                    textInstituicoes.setText(Objects.requireNonNull(dataSnapshot.child(idMotorista).child("instituicoesAtendidas").getValue()).toString());
                    textTelefone.setText(Objects.requireNonNull(dataSnapshot.child(idMotorista).child("telefone").getValue()).toString());
                    numeroCliques.setText(Objects.requireNonNull(dataSnapshot.child(idMotorista).child("acessos").getValue()).toString());
                    if (!motorista.getUrlPerfil().equals("")) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            uri = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                switch (numeroImagem) {
                    case 1:
                        uriMap.put("perfil", uri);
                        imagemPerfil.setImageBitmap(bitmap);
                        break;
                    case 2:
                        uriMap.put("van1", uri);
                        imagemVan1.setImageBitmap(bitmap);
                        break;
                    case 3:
                        uriMap.put("van2", uri);
                        imagemVan2.setImageBitmap(bitmap);
                        break;
                    case 4:
                        uriMap.put("van3", uri);
                        imagemVan3.setImageBitmap(bitmap);
                        break;
                    case 5:
                        uriMap.put("van4", uri);
                        imagemVan4.setImageBitmap(bitmap);
                        break;
                    default:
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage(final String identificador, final OnCompleteUpload completeUpload) {
        if (uri != null) {
            // Defining the child of storageReference
            final String path = "imagens/" + Base64.encodeToString(motorista.getEmail().getBytes(), Base64.DEFAULT).replaceAll("([\\n\\r])", "") + " " + identificador;
            final StorageReference ref = storageReference.child(path);

            if (!uriMap.containsKey(identificador)  || uriMap.get(identificador) == null)
                completeUpload.onComplete(null);

            try {
                final UploadTask uploadTask = ref.putFile(Objects.requireNonNull(uriMap.get(identificador)));
                uploadTask.addOnSuccessListener(task -> {
                    Toast.makeText(PrincipalMotorista.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    uploadTask.continueWithTask(task1 -> {
                        if (!task1.isSuccessful()) {
                            throw Objects.requireNonNull(task1.getException());
                        }

                        return ref.getDownloadUrl();
                    }).addOnCompleteListener(task12 -> {
                        if (task12.isSuccessful()) {
                            completeUpload.onComplete(Objects.requireNonNull(task12.getResult()).toString());
                        }
                    });
                }).addOnFailureListener(e -> {
                    // Error, Image not uploaded
                    Toast.makeText(PrincipalMotorista.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } catch (Exception ignored){

            }
        }
    }

    private void atualizar(final Motorista motorista, final DatabaseReference databaseReference) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child(motorista.getId()).removeValue();
                databaseReference.child(motorista.getId()).setValue(motorista);
                Toast.makeText(getApplicationContext(), "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    interface OnCompleteUpload {
        void onComplete(String url);
    }
}
