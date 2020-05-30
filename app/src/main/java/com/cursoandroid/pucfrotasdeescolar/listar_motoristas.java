package com.cursoandroid.pucfrotasdeescolar;

import androidx.appcompat.app.AppCompatActivity;

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

public class listar_motoristas extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference contaoDatabaseReference = databaseReference.child("Motorista");
    private ArrayAdapter<Motorista> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_motoristas);

        List<Motorista> motoristas;
        listView = (ListView) findViewById(R.id.listview_id);
        listView.setAdapter(null);

        contaoDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Motorista> motoristas = new ArrayList<Motorista>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Motorista motorista = new Motorista();
                    motorista.setNome(postSnapshot.child("nome").getValue().toString());
                    motorista.setEmail(postSnapshot.child("email").getValue().toString());
                    //motorista.setSenha(postSnapshot.child("senha").getValue().toString());
                    motorista.setInstituicoesAtendidas(postSnapshot.child("instituicoesAtendidas").getValue().toString());
                    //motorista.setLocaisAtendidos(postSnapshot.child("locaisAtendidos").getValue().toString());
                    //motorista.setTelefone(postSnapshot.child("telefone").getValue().toString());
                    //motorista.setAcessos(Integer.parseInt(postSnapshot.child("acessos").getValue().toString()));
                    motoristas.add(motorista);
                    motorista = null;
                }
                adapter = new
                        ArrayAdapter<Motorista>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        motoristas);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String itemSelecionado = (String) parent.getItemAtPosition(position);
                String email = itemSelecionado.split("\n")[1];

                String idMotorista = Base64.encodeToString(email.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

                // direcionar para a tela do motorista mandando o id do motorista
            }
        });
    }
}
