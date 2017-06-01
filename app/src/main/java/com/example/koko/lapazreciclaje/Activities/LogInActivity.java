package com.example.koko.lapazreciclaje.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText etCorreo,etContrasena;
    private Button btnIniciarSesion,btnCrearCuenta;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    finish();
                    startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                }
            }
        };
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContrasena = (EditText) findViewById(R.id.etContrasena);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistroUsuarioActivity.class));
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);

    }

    private void iniciarSesion() {
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        progressDialog.setMessage("Iniciando Sesión");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"No se pudo iniciar sesión.",Toast.LENGTH_LONG).show();
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
