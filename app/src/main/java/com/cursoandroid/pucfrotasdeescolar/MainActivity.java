package com.cursoandroid.pucfrotasdeescolar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText nomeUsuario;
    private EditText senhaUsuario;
    private Button entrar;
    private Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(new Usuario(nomeUsuario.getText().toString(), senhaUsuario.getText().toString()));
            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Vai para layout de cadastro
            }
        });

    }

    private void inicializar() {
        nomeUsuario = findViewById(R.id.edit_nome);
        senhaUsuario = findViewById(R.id.edit_senha);
        entrar = findViewById(R.id.botao_entrar);
        cadastrar = findViewById(R.id.botao_criar_conta);
    }

    private boolean login(Usuario us) {
        List<Usuario> usuarioList = new ArrayList<>();
        boolean status = false;

        try {
            SQLiteDatabase database = getApplicationContext().openOrCreateDatabase("usuarios", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT id, nome, senha FROM Usuario WHERE nome = '" + us.getNome() + "' AND senha = '" + us.getSenha() + "'", null);

            int idIndex = cursor.getColumnIndex("id");
            int nomeIndex = cursor.getColumnIndex("nome");
            int senhaIndex = cursor.getColumnIndex("senha");

            cursor.moveToFirst();
            while (cursor.getString(idIndex) != null) {
                usuarioList.add(new Usuario(cursor.getInt(idIndex), cursor.getString(nomeIndex), cursor.getString(senhaIndex)));
                cursor.moveToNext();
            }

            cursor.close();

            if (!usuarioList.isEmpty()) {
                status = true;
            }

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
        }

        return status;
    }

}
