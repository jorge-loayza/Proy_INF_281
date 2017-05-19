package com.example.koko.lapazreciclaje;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.Adapters.ArticulosAdapter;
import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class PerfilUsuarioActivity extends AppCompatActivity {




    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceArticulos;

    private RecyclerView rvListaArticulos;
    private List<Articulo> listaArticulos;
    private ArticulosAdapter articulosAdapter;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser == null){
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        };
        rvListaArticulos = (RecyclerView) findViewById(R.id.rvListaArticulos);
        rvListaArticulos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listaArticulos = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        query = firebaseDatabase.getReference().child("articulo")
                .orderByChild("id_usuario").equalTo(firebaseAuth.getCurrentUser().getUid().toString());
        query.keepSynced(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil_usuario,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salir) {
            FirebaseAuth.getInstance().signOut();
            return true;
        }
        if (id == R.id.adicionar) {
            startActivity(new Intent(getApplicationContext(),RedactarArticuloActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

        FirebaseRecyclerAdapter<Articulo,ArticulosAdapter.ArticulosViewHolder> adapter = new FirebaseRecyclerAdapter<Articulo, ArticulosAdapter.ArticulosViewHolder>(
                Articulo.class,
                R.layout.ariticulo_row,
                ArticulosAdapter.ArticulosViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(ArticulosAdapter.ArticulosViewHolder viewHolder, Articulo model, int position) {
                viewHolder.setTitulo(model.getTitulo());
                viewHolder.setDescripcion(model.getDecripcion());

                viewHolder.setImage(getApplicationContext(),model.getImagen());


            }
        };
        rvListaArticulos.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
