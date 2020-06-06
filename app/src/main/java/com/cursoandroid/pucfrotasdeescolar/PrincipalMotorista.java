package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    public Uri uri;
    public int numeroImagem;

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

        imagemPerfil = (ImageView) findViewById(R.id.img_motorista);
        imagemVan1 = (ImageView) findViewById(R.id.imageView);
        imagemVan2 = (ImageView) findViewById(R.id.imageView2);
        imagemVan3 = (ImageView) findViewById(R.id.imageView3);
        imagemVan4 = (ImageView) findViewById(R.id.imageView4);


        intent = getIntent();
        email = intent.getStringExtra("email");

        imprimeTela();

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        imagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 1;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("perfil");
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem de perfil."), 123);
            }
        });

        imagemVan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 2;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van1");
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 3;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van2");
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 4;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van3");
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 5;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                uploadImage("van4");
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String descricao = textDescricao.getText().toString();
                final String bairro = textBairro.getText().toString();
                final String telefone = textTelefone.getText().toString();
                final String instituicoes = textInstituicoes.getText().toString();

                if ((!descricao.equals("")) && (!bairro.equals("")) && (!telefone.equals("")) && (telefone.length() <= 9) && (!instituicoes.equals(""))) {
                    motorista.setDescricao(descricao);
                    motorista.setLocaisAtendidos(bairro);
                    motorista.setInstituicoesAtendidas(instituicoes);
                    motorista.setTelefone(telefone);
                    create(motorista, motoristaDataBase);
                }
            }
        });

    }

    private void imprimeTela() {
        motoristaDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idMotorista = Base64.encodeToString(email.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
                boolean motoristaCadastrado = dataSnapshot.hasChild(idMotorista);
                if (motoristaCadastrado) {
                    motorista.setEmail(email);
                    motorista.setNome(dataSnapshot.child(idMotorista).child("nome").getValue().toString());
                    motorista.setDescricao(dataSnapshot.child(idMotorista).child("descricao").getValue().toString());
                    motorista.setTelefone(dataSnapshot.child(idMotorista).child("telefone").getValue().toString());
                    motorista.setInstituicoesAtendidas(dataSnapshot.child(idMotorista).child("instituicoesAtendidas").getValue().toString());
                    motorista.setAcessos(Integer.parseInt(dataSnapshot.child(idMotorista).child("acessos").getValue().toString()));
                    motorista.setLocaisAtendidos(dataSnapshot.child(idMotorista).child("locaisAtendidos").getValue().toString());
                } else {
                    motorista = null;
                }

                if (motorista != null) {
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
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage(String identificador) {
        if (uri != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            final String path = "imagens/"+ Base64.encodeToString(email.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "") + " " + identificador;
            final StorageReference ref = storageReference.child(path);

            ref.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ref.delete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // adding listeners on upload
                    // or failure of image
                    ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Image uploaded successfully
                            // Dismiss dialog
                            progressDialog.dismiss();
                            Toast.makeText(PrincipalMotorista.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(PrincipalMotorista.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
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
}
