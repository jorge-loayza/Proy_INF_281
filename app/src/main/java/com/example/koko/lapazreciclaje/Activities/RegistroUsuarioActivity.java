package com.example.koko.lapazreciclaje.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.Objetos.Usuario;
import com.example.koko.lapazreciclaje.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroUsuarioActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnCancelar,btnRegistrar;
    private EditText etNombres,etApellidos,etCorreo,etContrasena;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PATTERN_NAME = "^[A-Za-z ]";

    private TextInputLayout txtInputNombres,txtInputApellidos,txtInputCorreo,txtInputContrasena;

    private Boolean sw =false;
    private Boolean sw2 =false;
    private Boolean sw3 =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);



        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference("usuario");
        etNombres = (EditText) findViewById(R.id.etNombres);
        etApellidos = (EditText) findViewById(R.id.etApellidos);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etContrasena = (EditText) findViewById(R.id.etContrasena);

        txtInputNombres = (TextInputLayout) findViewById(R.id.txtInputNombres);
        txtInputApellidos = (TextInputLayout) findViewById(R.id.txtInputApellidos);
        txtInputCorreo = (TextInputLayout) findViewById(R.id.txtInputCorreo);
        txtInputContrasena = (TextInputLayout) findViewById(R.id.txtInputContrasena);

        progressDialog = new ProgressDialog(this);

        etNombres.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    txtInputNombres.setErrorEnabled(false);
                    if (validateName(etNombres.getText().toString().trim())){
                        sw = true;
                    }else {
                        txtInputNombres.setError("Nombre invalido.");
                        sw = false;
                    }
                }else {
                    txtInputNombres.setErrorEnabled(false);
                }
            }
        });

        etApellidos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    txtInputApellidos.setErrorEnabled(false);
                    if (validateName(etApellidos.getText().toString().trim())){
                        sw2 = true;
                    }else {
                        txtInputApellidos.setError("Apellido invalido.");
                        sw2 = false;
                    }
                }else {
                    txtInputNombres.setErrorEnabled(false);
                }
            }
        });

        etCorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    txtInputCorreo.setErrorEnabled(false);
                    if (validateEmail(etCorreo.getText().toString().trim())){
                        sw3 = true;
                    }else {
                        txtInputCorreo.setError("Correo invalido.");
                        sw3 = false;
                    }
                }else {
                    txtInputCorreo.setErrorEnabled(false);
                }
            }
        });

        etContrasena.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    txtInputContrasena.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancelar:
                finish();
                startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                break;
            case R.id.btnRegistrar:
                iniciarRegistro();
                break;
        }
    }

    private void iniciarRegistro() {

        final String nombre = etNombres.getText().toString().trim();
        final String apellidos = etApellidos.getText().toString().trim();
        final String correo = etCorreo.getText().toString().trim();
        final String contrasena = etContrasena.getText().toString().trim();
        progressDialog.setMessage("Registrando Usuario");

        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contrasena)){
            if (sw && sw2 && sw3){
                if (validatePassword(contrasena)){

                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Usuario usuario = new Usuario(nombre,apellidos,correo,user.getUid().toString());
                                databaseReference.child(user.getUid().toString()).setValue(usuario);
                                startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                                finish();

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"No se pudo crear el Usuario",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else {
                    txtInputContrasena.setError("Contraseña corta.");
                }
            }
        }else{
            Toast.makeText(getApplicationContext(),"Debe llenar Todos los Campos",Toast.LENGTH_LONG).show();
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





}
