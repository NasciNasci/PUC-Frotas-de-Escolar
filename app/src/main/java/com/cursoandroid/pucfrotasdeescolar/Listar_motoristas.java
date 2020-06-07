package com.cursoandroid.pucfrotasdeescolar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class Listar_motoristas extends AppCompatActivity implements OnClickListener<Motorista> {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference contaoDatabaseReference = databaseReference.child("Motorista");
    private MotoristaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_motoristas);
        adapter = new MotoristaAdapter(this);
        RecyclerView listaMotorista = (RecyclerView) findViewById(R.id.listview_id);
        listaMotorista.setAdapter(adapter);

        contaoDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Motorista> motoristas = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Motorista motorista = new Motorista();
                    motorista.setNome(postSnapshot.child("nome").getValue().toString());
                    motorista.setEmail(postSnapshot.child("email").getValue().toString());
                    motorista.setSenha(postSnapshot.child("senha").getValue().toString());
                    motorista.setInstituicoesAtendidas(postSnapshot.child("instituicoesAtendidas").getValue().toString());
                    motorista.setLocaisAtendidos(postSnapshot.child("locaisAtendidos").getValue().toString());
                    motorista.setTelefone(postSnapshot.child("telefone").getValue().toString());
                    motorista.setId(postSnapshot.child("id").getValue().toString());
                    motorista.setDescricao(postSnapshot.child("descricao").getValue().toString());
                    motorista.setAcessos(Integer.parseInt(postSnapshot.child("acessos").getValue().toString()));
                    motoristas.add(motorista);
                }
                adapter.setList(motoristas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(Motorista motorista) {
        Intent intent = new Intent(Listar_motoristas.this, PrincipalMotorista.class);
        intent.putExtra("email", motorista.getEmail());
        intent.putExtra("id", motorista.getId());
        intent.putExtra("nome", motorista.getNome());
        intent.putExtra("senha", motorista.getSenha());
        intent.putExtra("instituicoesAtendidas", motorista.getInstituicoesAtendidas());
        intent.putExtra("locaisAtendidos", motorista.getLocaisAtendidos());
        intent.putExtra("telefone", motorista.getTelefone());
        intent.putExtra("descricao", motorista.getDescricao());
        intent.putExtra("acessos", motorista.getAcessos());
        startActivity(intent);
    }
}

interface OnClickListener<T> {
    void onClick(T T);
}


