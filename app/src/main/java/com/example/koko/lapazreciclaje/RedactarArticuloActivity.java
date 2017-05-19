package com.example.koko.lapazreciclaje;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RedactarArticuloActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etTituloArticulo,etDecripcionArticulo,etContenidoArticulo;
    private ImageView ivImagenArticulo;
    private Button btnAgregarImagen,btnCrearArticulo;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceArticulo;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redactar_articulo);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceArticulo = firebaseDatabase.getReference().child("articulo");
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        };

        etTituloArticulo = (EditText) findViewById(R.id.etTituloArticulo);
        etDecripcionArticulo = (EditText) findViewById(R.id.etDecripcionArticulo);
        etContenidoArticulo = (EditText) findViewById(R.id.etContenidoArticulo);
        ivImagenArticulo = (ImageView) findViewById(R.id.ivImagenArticulo);
        btnAgregarImagen = (Button) findViewById(R.id.btnAgregarImagen);
        btnCrearArticulo = (Button) findViewById(R.id.btnCrearArticulo);
        btnAgregarImagen.setOnClickListener(this);
        btnCrearArticulo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregarImagen:
                break;
            case R.id.btnCrearArticulo:
                registrarArticulo();
                break;
        }
    }

    private void registrarArticulo() {
        String titulo = etTituloArticulo.getText().toString().trim();
        String descripcion = etDecripcionArticulo.getText().toString().trim();
        String contenido = etContenidoArticulo.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Articulo articulo = new Articulo(titulo,descripcion,contenido,user.getUid().toString());
        databaseReferenceArticulo.push().setValue(articulo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    finish();
                    Toast.makeText(getApplicationContext(),"Se registro el articulo",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"No se registro el articulo",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
