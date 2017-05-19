package com.example.koko.lapazreciclaje;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnPapel,btnPlasticos,btnVidrio,btnDesechosPeligrosos,btnDesechosOrganicos,btnRestoResiduos,btnIniciarSesion,btnCrearCuenta;

    private Intent intent;


    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btnPapel = (Button) findViewById(R.id.btnPapelCarton);
        btnPlasticos = (Button) findViewById(R.id.btnPlasticosLatas);
        btnVidrio = (Button) findViewById(R.id.btnVidrio);
        btnDesechosPeligrosos = (Button) findViewById(R.id.btnDesechosPeligrosos);
        btnDesechosOrganicos = (Button) findViewById(R.id.btnDesechosOrganicos);
        btnRestoResiduos = (Button) findViewById(R.id.btnRestosResiduos);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);

        btnPapel.setOnClickListener(this);
        btnPlasticos.setOnClickListener(this);
        btnVidrio.setOnClickListener(this);
        btnDesechosPeligrosos.setOnClickListener(this);
        btnDesechosOrganicos.setOnClickListener(this);
        btnRestoResiduos.setOnClickListener(this);
        btnIniciarSesion.setOnClickListener(this);
        btnCrearCuenta.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    btnCrearCuenta.setVisibility(View.GONE);
                    btnIniciarSesion.setText("Ir al Perfil");
                }
            }
        };

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnPapelCarton :
                intent = new Intent(getApplicationContext(),Categoria.class);
                intent.putExtra("categoria",1);
                startActivity(intent);
                break;
            case R.id.btnPlasticosLatas :
                intent = new Intent(getApplicationContext(),Categoria.class);
                intent.putExtra("categoria",2);
                startActivity(intent);
                break;
            case R.id.btnVidrio :
                intent = new Intent(getApplicationContext(),Categoria.class);
                intent.putExtra("categoria",3);
                startActivity(intent);
                break;
            case R.id.btnDesechosPeligrosos :
                intent = new Intent(getApplicationContext(),Categoria.class);
                intent.putExtra("categoria",4);
                startActivity(intent);
                break;
            case R.id.btnDesechosOrganicos :
                intent = new Intent(getApplicationContext(),Categoria.class);
                intent.putExtra("categoria",5);
                startActivity(intent);
                break;
            case R.id.btnRestosResiduos :
                intent = new Intent(getApplicationContext(),Categoria.class);
                intent.putExtra("categoria",6);
                startActivity(intent);
                break;
            case R.id.btnIniciarSesion:
                intent = new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCrearCuenta:
                intent = new Intent(getApplicationContext(),RegistroUsuarioActivity.class);
                startActivity(intent);
                break;
        }

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
