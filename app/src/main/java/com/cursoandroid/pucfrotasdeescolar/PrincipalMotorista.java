package com.cursoandroid.pucfrotasdeescolar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalMotorista extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference motoristaDataBase = databaseReference.child("Motorista");

    private Intent intent;

    private CardView cardView;
    private ImageView imagemPerfil;

    private ImageView imagemVan1;
    private ImageView imagemVan2;
    private ImageView imagemVan3;
    private ImageView imagemVan4;
    //private static final int IMAGE_GALLERY_REQUEST = 1;
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

      /*  intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        imagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 1;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem de perfil."), 123);
            }
        });

        imagemVan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 2;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 3;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 4;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });

        imagemVan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroImagem = 5;
                startActivityForResult(intent, IMAGE_GALLERY_REQUEST);
                //startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem."), 123);
            }
        });*/

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
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha os campos solicitados.", Toast.LENGTH_SHORT).show();
                }
                //uploadFirebase();
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

    private void create(final Usuario usuario, final DatabaseReference databaseReference) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario.setId(Base64.encodeToString(usuario.getEmail().getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", ""));
                boolean cadastrado = dataSnapshot.hasChild(usuario.getId());

                if (!cadastrado) {
                    databaseReference.child(usuario.getId()).setValue(usuario);
                    Toast.makeText(getApplicationContext(), "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show();

                    if (usuario.getClass().equals(Motorista.class)) {
                        Intent intent = new Intent(getApplicationContext(), PrincipalMotorista.class);
                        intent.putExtra("email", usuario.getEmail());
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(getApplicationContext(), Listar_motoristas.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário já cadastrado anteriormente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            //uri = data.getData();

        //}
        if(requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK && data!= null){
            uri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(uri,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            switch(numeroImagem){
                case 1:
                    imagemPerfil.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                case 2:
                    imagemVan1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                case 3:
                    imagemVan2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                case 4:
                    imagemVan3.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                case 5:
                    imagemVan4.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                default:
            }

        }
    }*/
/*
    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFirebase(){
        uri = Uri.fromFile(new File("images/" + motorista.getId()));
        StorageReference idRef = storageReference.child("")
    }*/
}
