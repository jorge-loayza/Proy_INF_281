package com.example.koko.lapazreciclaje.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText etCorreo,etContrasena;
    private TextInputLayout textInputCorreo,textInputContrasena;
    private Button btnIniciarSesion,btnCrearCuenta;
    private ProgressDialog progressDialog;

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PATTERN_NAME = "^[A-Za-z]";

    private Boolean sw1 = false;
    private Boolean sw2 = false;

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
        textInputCorreo = (TextInputLayout) findViewById(R.id.textInputCorreo);
        textInputContrasena = (TextInputLayout) findViewById(R.id.textInputContrasena);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etCorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    textInputCorreo.setErrorEnabled(false);
                    if (validateEmail(etCorreo.getText().toString().trim())){
                        sw1 = true;
                    }else {
                        textInputCorreo.setError("Correo invalido.");
                        sw1 = false;
                    }
                }else {
                    textInputCorreo.setErrorEnabled(false);
                }
            }
        });
        etContrasena = (EditText) findViewById(R.id.etContrasena);
        etContrasena.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    textInputContrasena.setErrorEnabled(false);
                }
            }
        });
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


        if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contrasena)){

            if (sw1){
                if (validatePassword(etContrasena.getText().toString().trim())){
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
                }else {
                    textInputContrasena.setError("Contraseña invalida");
                }

            }
        }else{
            Toast.makeText(getApplicationContext(),"Debe ingresar ambos campos.",Toast.LENGTH_LONG).show();
        }



    }

    private boolean validatePassword(String password) {
        if (password.length() >= 8){
            return true;
        }else{
            return false;
        }
    }

    private static boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
    private static boolean validateName(String name) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_NAME);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();

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
