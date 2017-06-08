package com.example.koko.lapazreciclaje.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.example.koko.lapazreciclaje.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ArticuloActivity extends AppCompatActivity {

    private Articulo articulo;
    private ImageView ivImagenArticulo;
    private TextView tvTitArticulo,tvDesArticulo;
    private Button btnLeerDocumento;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarArticulo);
        setSupportActionBar(toolbar);
        articulo = (Articulo) getIntent().getSerializableExtra("articulo");
        ivImagenArticulo = (ImageView) findViewById(R.id.ivImagenArticulo);
        tvTitArticulo = (TextView) findViewById(R.id.tvTitArticulo);
        tvDesArticulo = (TextView) findViewById(R.id.tvDesArticulo);
        setImage(articulo.getImagen());
        tvTitArticulo.setText(articulo.getTitulo());
        tvDesArticulo.setText(articulo.getDecripcion());
        btnLeerDocumento = (Button) findViewById(R.id.btnLeerDocumento);
        if (articulo.getPdf() != null){
            btnLeerDocumento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //descargarPDF();
                    verificaPermiso();
                }
            });
        }else{
            btnLeerDocumento.setVisibility(View.GONE);
        }
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
    }

    public void setImage(final String image){


        Picasso.with(getApplicationContext()).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(ivImagenArticulo, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(getApplicationContext()).load(image).into(ivImagenArticulo);
            }
        });

    }
    public void descargarPDF(){

        progressDialog.setMessage("Descargando PDF");
        progressDialog.show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PDF_Articulos").child(articulo.getId_articulo());
        File storagePath = new File(Environment.getExternalStorageDirectory(),"La_Paz_Reciclaje");

        if(!storagePath.exists()) {
            storagePath.mkdirs();
        }

        final File myFile = new File(storagePath,articulo.getTitulo()+".pdf");
        if (!myFile.exists()){
            storageReference.getFile(myFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    Uri data = Uri.parse("file://"+myFile.getAbsoluteFile());
                    intent.setDataAndType(data,"application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    myFile.delete();
                    Toast.makeText(getApplicationContext(),"Error al Descargar",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            progressDialog.dismiss();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
            Uri data = Uri.parse("file://"+myFile.getAbsoluteFile());
            intent.setDataAndType(data,"application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        }

    }

    public void verificaPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {

            }
        } else {
            descargarPDF();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    descargarPDF();
                } else {
                    Toast.makeText(getApplicationContext(),"Debe conceder permisos para guardar la imagen.",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


}
