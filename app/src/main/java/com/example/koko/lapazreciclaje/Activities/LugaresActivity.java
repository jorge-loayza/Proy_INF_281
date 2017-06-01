package com.example.koko.lapazreciclaje.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.koko.lapazreciclaje.Adapters.LugaresAdapter;
import com.example.koko.lapazreciclaje.Objetos.LugarReciclaje;
import com.example.koko.lapazreciclaje.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class LugaresActivity extends AppCompatActivity {

    private String categoria;
    private RecyclerView rvLugares;
    private FirebaseDatabase firebaseDatabase;
    private Query queryLugares;
    private List<LugarReciclaje> listaDeLugares;
    private LugaresAdapter lugaresAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);
        categoria = getIntent().getStringExtra("categoria");

        firebaseDatabase = FirebaseDatabase.getInstance();
        queryLugares = firebaseDatabase.getReference().child("lugare_reciclaje").orderByChild("categoria").equalTo(categoria);
        listaDeLugares = new ArrayList<>();
        rvLugares = (RecyclerView) findViewById(R.id.rvLugares);
        lugaresAdapter = new LugaresAdapter(listaDeLugares);
        rvLugares.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvLugares.setAdapter(lugaresAdapter);
        mostrarPuntos(categoria);
    }

    private void mostrarPuntos(String categoria) {
        listaDeLugares.clear();
        queryLugares.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LugarReciclaje lugar = dataSnapshot.getValue(LugarReciclaje.class);
                Log.i("dato",lugar.getNombre());
                listaDeLugares.add(lugar);
                lugaresAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
