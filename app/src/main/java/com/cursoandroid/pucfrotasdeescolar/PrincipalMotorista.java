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

public class PrincipalMotorista extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference motoristaDataBase = databaseReference.child("Motorista");

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Intent intent;

    private ImageView imagemPerfil;

    private ImageView imagemVan1;
    private ImageView imagemVan2;
    private ImageView imagemVan3;
    private ImageView imagemVan4;
    private static final int IMAGE_GALLERY_REQUEST = 1;

    private Motorista motorista = new Motorista();

    private EditText textDescricao;
    private EditText textBairro;
    private EditText textTelefone;
    private EditText textInstituicoes;
    private TextView numeroCliques;
    private TextView nome;
    private TextView textViewEmail;
    private Button buttonSalvar;
    private String email;

    private String urlPerfil = motorista.getUrlPerfil();
    private String urlVan1 = motorista.getUrlVan1();
    private String urlVan2 = motorista.getUrlVan2();
    private String urlVan3 = motorista.getUrlVan3();
    private String urlVan4 = motorista.getUrlVan4();

    public Uri uri;
    public int numeroImagem;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_motorista);

        textDescricao = findViewById(R.id.editText_descricao);
        textBairro = findViewById(R.id.editText_bairros);
        textTelefone = findViewById(R.id.editText_telefone);
        textInstituicoes = findViewById(R.id.editText_instuicoes);
        numeroCliques = findViewById(R.id.txt_quantidade_acessos);
        nome = findViewById(R.id.txt_nome);
        textViewEmail = findViewById(R.id.txt_email);
        buttonSalvar = findViewById(R.id.botao_salvar);

        imagemPerfil = findViewById(R.id.img_motorista);
        imagemVan1 = findViewById(R.id.imageView);
        imagemVan2 = findViewById(R.id.imageView2);
        imagemVan3 = findViewById(R.id.imageView3);
        imagemVan4 = findViewById(R.id.imageView4);

        intent = getIntent();
        motorista = (Motorista) intent.getSerializableExtra("motorista");

        urlPerfil = motorista.getUrlPerfil();
        urlVan1 = motorista.getUrlVan1();
        urlVan2 = motorista.getUrlVan2();
        urlVan3 = motorista.getUrlVan3();
        urlVan4 = motorista.getUrlVan4();

        imprimeTela();

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        imagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 1;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                //uploadImage("perfil");
                uploadImage("perfil", new OnCompleteUpload() {
                    @Override
                    public void onComplete(String url) {
                        urlPerfil = url;
                    }
                });

                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem de perfil."), 123);
            }
        });

        imagemVan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 2;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van1", new OnCompleteUpload() {
                    @Override
                    public void onComplete(String url) {
                        urlVan1 = url;
                    }
                });

                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 3;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van2", new OnCompleteUpload() {
                    @Override
                    public void onComplete(String url) {
                        urlVan2 = url;
                    }
                });

                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 4;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van3", new OnCompleteUpload() {
                    @Override
                    public void onComplete(String url) {
                        urlVan3 = url;
                    }
                });

                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 5;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van4", new OnCompleteUpload(){

                    @Override
                    public void onComplete(String url) {
                        urlVan4 = url;
                    }
                });

                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = textDescricao.getText().toString();
                String bairro = textBairro.getText().toString();
                String telefone = textTelefone.getText().toString();
                String instituicoes = textInstituicoes.getText().toString();
//
//                motorista.setDescricao(descricao);
//                motorista.setLocaisAtendidos(bairro);
//                motorista.setInstituicoesAtendidas(instituicoes);
//                motorista.setTelefone(telefone);
//                motorista.setSenha(motorista.getSenha());
//                motorista.setNome(motorista.getNome());
//                motorista.setId(motorista.getId());
//                motorista.setAcessos(motorista.getAcessos());
//                motorista.setEmail(motorista.getSenha());
                motorista.setDescricao(descricao);
                motorista.setLocaisAtendidos(bairro);
                motorista.setTelefone(telefone);
                motorista.setInstituicoesAtendidas(instituicoes);
                motorista.setUrlPerfil(urlPerfil);
                motorista.setUrlVan1(urlVan1);
                motorista.setUrlVan2(urlVan2);
                motorista.setUrlVan3(urlVan3);
                motorista.setUrlVan4(urlVan4);
                atualizar(motorista, motoristaDataBase);
            }
        });

    }

    private void imprimeTela() {

        motoristaDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idMotorista = Base64.encodeToString(motorista.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                boolean motoristaCadastrado = dataSnapshot.hasChild(idMotorista);
                if (motoristaCadastrado) {
//                    motorista.setEmail(email);
//                    motorista.setNome(dataSnapshot.child(idMotorista).child("nome").getValue().toString());
//                    motorista.setDescricao(dataSnapshot.child(idMotorista).child("descricao").getValue().toString());
//                    motorista.setTelefone(dataSnapshot.child(idMotorista).child("telefone").getValue().toString());
//                    motorista.setInstituicoesAtendidas(dataSnapshot.child(idMotorista).child("instituicoesAtendidas").getValue().toString());
//                    motorista.setAcessos(Integer.parseInt(dataSnapshot.child(idMotorista).child("acessos").getValue().toString()));
//                    motorista.setLocaisAtendidos(dataSnapshot.child(idMotorista).child("locaisAtendidos").getValue().toString());

                    textDescricao.setText(dataSnapshot.child(idMotorista).child("descricao").getValue().toString());
                    textBairro.setText(dataSnapshot.child(idMotorista).child("locaisAtendidos").getValue().toString());
                    textInstituicoes.setText(dataSnapshot.child(idMotorista).child("instituicoesAtendidas").getValue().toString());
                    textTelefone.setText(dataSnapshot.child(idMotorista).child("telefone").getValue().toString());
                    numeroCliques.setText(dataSnapshot.child(idMotorista).child("acessos").getValue().toString());
//                    motorista.setUrlPerfil(dataSnapshot.child(idMotorista).child("urlPerfil").getValue().toString());
//                    motorista.setUrlVan1(dataSnapshot.child(idMotorista).child("urlVan1").getValue().toString());
//                    motorista.setUrlVan2(dataSnapshot.child(idMotorista).child("urlVan2").getValue().toString());
//                    motorista.setUrlVan3(dataSnapshot.child(idMotorista).child("urlVan3").getValue().toString());
//                    motorista.setUrlVan4(dataSnapshot.child(idMotorista).child("urlVan4").getValue().toString());
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
                }
//                else {
//                    motorista = null;
//                }

//                if (motorista != null) {
//                    nome.setText(motorista.getNome());
//                    textViewEmail.setText(motorista.getEmail());
//                    textDescricao.setText(motorista.getDescricao());
//                    textBairro.setText(motorista.getLocaisAtendidos());
//                    textInstituicoes.setText(motorista.getInstituicoesAtendidas());
//                    textTelefone.setText(motorista.getTelefone());
//                    numeroCliques.setText(Integer.toString(motorista.getAcessos()));
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (motorista != null) {
            nome.setText(motorista.getNome());
            textViewEmail.setText(motorista.getEmail());
//            textDescricao.setText(motorista.getDescricao());
//            textBairro.setText(motorista.getLocaisAtendidos());
//            textInstituicoes.setText(motorista.getInstituicoesAtendidas());
//            textTelefone.setText(motorista.getTelefone());
//            numeroCliques.setText(Integer.toString(motorista.getAcessos()));
        }
    }

    /*

        // Select Image method
        private void SelectImage()
        {

            // Defining Implicit Intent to mobile gallery
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select Image from here..."),
                PICK_IMAGE_REQUEST);
        }
     */

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            uri = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                switch (numeroImagem) {
                    case 1:
                        imagemPerfil.setImageBitmap(bitmap);
                        break;
                    case 2:
                        imagemVan1.setImageBitmap(bitmap);
                        break;
                    case 3:
                        imagemVan2.setImageBitmap(bitmap);
                        break;
                    case 4:
                        imagemVan3.setImageBitmap(bitmap);
                        break;
                    case 5:
                        imagemVan4.setImageBitmap(bitmap);
                        break;
                    default:
                }
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage(final String identificador) {
        if (uri != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            final String path = "imagens/" + Base64.encodeToString(motorista.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "") + " " + identificador;
            final StorageReference ref = storageReference.child(path);

            final UploadTask uploadTask = ref.putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot task) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(PrincipalMotorista.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                url = task.getResult().toString();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(PrincipalMotorista.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    private void uploadImage(final String identificador, final OnCompleteUpload completeUpload) {
        if (uri != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            final String path = "imagens/" + Base64.encodeToString(motorista.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "") + " " + identificador;
            final StorageReference ref = storageReference.child(path);

            final UploadTask uploadTask = ref.putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot task) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(PrincipalMotorista.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                completeUpload.onComplete(task.getResult().toString());
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(PrincipalMotorista.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
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
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário já cadastrado anteriormente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void atualizar(final Motorista motorista, final DatabaseReference databaseReference) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                databaseReference.child(motorista.getId()).removeValue();
                databaseReference.child(motorista.getId()).setValue(motorista);
                Toast.makeText(getApplicationContext(), "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    interface OnCompleteUpload {
        void onComplete(String url);
    }
}
