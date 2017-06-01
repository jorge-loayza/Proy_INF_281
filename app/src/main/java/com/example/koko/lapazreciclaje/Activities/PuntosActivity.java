package com.example.koko.lapazreciclaje.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.Objetos.Material;
import com.example.koko.lapazreciclaje.Objetos.LugarReciclaje;
import com.example.koko.lapazreciclaje.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PuntosActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int GALLERY_REQUEST = 1;
    private EditText nombrep,direccion,lat,longt,nombreMaterial,descripcionMaterial,categoria,categoriaLugar;
    private Button agrear,agrearMaterial;
    private ImageButton imagenMaterial;

    Uri imagenUri = null;

    StorageReference storageReference;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        nombrep = (EditText) findViewById(R.id.nombre);
        direccion = (EditText) findViewById(R.id.direccion);
        lat = (EditText) findViewById(R.id.latitud);
        longt = (EditText) findViewById(R.id.logitud);
        nombreMaterial = (EditText) findViewById(R.id.nombreMaterial);
        descripcionMaterial = (EditText) findViewById(R.id.descripcionMaterial);
        categoria = (EditText) findViewById(R.id.categoriaMaterial);
        categoriaLugar = (EditText) findViewById(R.id.categoriaLugar);

        imagenMaterial = (ImageButton) findViewById(R.id.ibImagenMaterial);

        agrear = (Button) findViewById(R.id.agrear);
        agrearMaterial = (Button) findViewById(R.id.agrearMaterial);

        agrear.setOnClickListener(this);
        agrearMaterial.setOnClickListener(this);
        imagenMaterial.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agrear:
                agrearPunto();
                break;
            case R.id.agrearMaterial:
                agregarMAterial();
                break;
            case R.id.ibImagenMaterial:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST);
                break;
        }
    }

    private void agrearPunto() {
        String nombre = nombrep.getText().toString().trim();
        String dir = direccion.getText().toString().trim();
        String latitud = lat.getText().toString().trim();
        String longiitud = longt.getText().toString().trim();
        String catLug = categoriaLugar.getText().toString().trim();

        LugarReciclaje punto = new LugarReciclaje(nombre,dir,latitud,longiitud,catLug);
        databaseReference.child("lugare_reciclaje").push().setValue(punto).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Se inserto el punto",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void agregarMAterial() {


        if (imagenUri != null){
            StorageReference ruta = storageReference.child("Imagenes_Tarjetas").child(imagenUri.getLastPathSegment());
            ruta.putFile(imagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    Material material = new Material(nombreMaterial.getText().toString().trim(),
                            descripcionMaterial.getText().toString().trim(),
                            categoria.getText().toString().trim(),
                            downloadUrl.toString());
                    databaseReference.child("material").push().setValue(material);
                    Toast.makeText(getApplicationContext(),"Se inserto el material",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            imagenUri = data.getData();
            imagenMaterial.setImageURI(imagenUri);

        }

    }
}
