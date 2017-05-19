package com.example.koko.lapazreciclaje;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RedactarArticuloActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GALLERY_REQUEST = 1;
    Uri imagenUri = null;
    private EditText etTituloArticulo,etDecripcionArticulo,etContenidoArticulo;
    private ImageView ivImagenArticulo;
    private Button btnAgregarImagen,btnCrearArticulo;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceArticulo;
    private StorageReference storageReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redactar_articulo);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceArticulo = firebaseDatabase.getReference().child("articulo");
        storageReference = FirebaseStorage.getInstance().getReference();
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


        progressDialog = new ProgressDialog(this);
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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST);
                break;
            case R.id.btnCrearArticulo:
                registrarArticulo();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            imagenUri = data.getData();
            ivImagenArticulo.setImageURI(imagenUri);

        }
    }

    private void registrarArticulo() {

        progressDialog.setMessage("Registrando Articulo...");
        final String titulo = etTituloArticulo.getText().toString().trim();
        final String descripcion = etDecripcionArticulo.getText().toString().trim();
        final String contenido = etContenidoArticulo.getText().toString().trim();
        final FirebaseUser user = firebaseAuth.getCurrentUser();


        //VAlidar Datos!!!!
        if (imagenUri != null){
            progressDialog.show();

            StorageReference ruta = storageReference.child("Imagenes_Articulos").child(user.getUid().toString());
            ruta.putFile(imagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Articulo articulo = new Articulo(titulo,descripcion,contenido,downloadUrl.toString(),user.getUid().toString());
                    databaseReferenceArticulo.push().setValue(articulo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                finish();
                                Toast.makeText(getApplicationContext(),"Se registro el articulo",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),PerfilUsuarioActivity.class));
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"No se registro el articulo",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(),"Debe seleccionar una imagen...",Toast.LENGTH_LONG).show();
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
