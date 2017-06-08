package com.example.koko.lapazreciclaje.Activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

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
    private ImageView ivDescripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCategoria);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsiongToolbarCategoria);

        categoria = getIntent().getStringExtra("categoria");
        firebaseDatabase = FirebaseDatabase.getInstance();
        queryLugares = firebaseDatabase.getReference().child("lugar_reciclaje").orderByChild("categoria").equalTo(categoria);
        listaDeLugares = new ArrayList<>();
        rvLugares = (RecyclerView) findViewById(R.id.rvLugares);
        lugaresAdapter = new LugaresAdapter(listaDeLugares);
        rvLugares.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvLugares.setAdapter(lugaresAdapter);
        ivDescripcion = (ImageView) findViewById(R.id.ivDescripcionLugar);
        mostrarPuntos(categoria);

        switch (categoria){
            case "1":
                ivDescripcion.setImageResource(R.drawable.papel_carton);
                collapsingToolbarLayout.setTitle(getString(R.string.titulo_papelcarton));
                break;
            case "2":
                ivDescripcion.setImageResource(R.drawable.plasticos_latas);
                collapsingToolbarLayout.setTitle(getString(R.string.titulo_plasticoslatas));
                break;
            case "3":
                ivDescripcion.setImageResource(R.drawable.vidrio);
                collapsingToolbarLayout.setTitle(getString(R.string.titulo_vidrio));
                break;
            case "4":
                ivDescripcion.setImageResource(R.drawable.desechos_peligrosos);
                collapsingToolbarLayout.setTitle(getString(R.string.titulo_desechosPeligrosos));
                break;
            case "5":
                ivDescripcion.setImageResource(R.drawable.desechos_organicos);
                collapsingToolbarLayout.setTitle(getString(R.string.titulo_desechosOrganicos));
                break;
            case "6":
                ivDescripcion.setImageResource(R.drawable.resto_residuos);
                collapsingToolbarLayout.setTitle(getString(R.string.titulo_restoResiduos));
                break;
        }
    }



    private void mostrarPuntos(String categoria) {
        listaDeLugares.clear();
        queryLugares.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LugarReciclaje lugar = dataSnapshot.getValue(LugarReciclaje.class);
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
