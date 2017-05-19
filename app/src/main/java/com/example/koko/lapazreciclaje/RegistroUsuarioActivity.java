package com.example.koko.lapazreciclaje;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.Objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistroUsuarioActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnCancelar,btnRegistrar;
    private EditText etNombres,etApellidos,etCorreo,etContrasena;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        getSupportActionBar().hide();
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
        progressDialog = new ProgressDialog(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancelar:
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.btnRegistrar:
                //finish();
                iniciarRegistro();
                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
    }

    private void iniciarRegistro() {
        final String nombre = etNombres.getText().toString().trim();
        final String apellidos = etApellidos.getText().toString().trim();
        final String correo = etCorreo.getText().toString().trim();
        final String contrasena = etContrasena.getText().toString().trim();
        final String telefono = etContrasena.getText().toString().trim();
        progressDialog.setMessage("Registrando Usuario");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    finish();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Usuario usuario = new Usuario(nombre,apellidos,correo,telefono);
                    databaseReference.child(user.getUid().toString()).setValue(usuario);
                    startActivity(new Intent(getApplicationContext(),PerfilUsuarioActivity.class));
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"No se pudo crear el Usuario",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
