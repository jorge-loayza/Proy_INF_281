package com.example.koko.lapazreciclaje.Activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.example.koko.lapazreciclaje.R;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class EditarArticuloActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GALLERY_REQUEST = 1;
    private static final int PICKFILE_REQUEST_CODE = 12;
    private Uri imagenUri = null;
    private Uri pdfUri = null;
    private EditText etTituloArticulo,etDecripcionArticulo;

    private Button btnCrearArticulo,btnCancelarRedaccion,btnAdicionarPDF;
    private ImageButton btnAgregarImagen;
    private TextView tvNombrePDf;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceArticulo;
    private StorageReference storageReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    ProgressDialog progressDialog;

    private Articulo articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_articulo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEditarArticulo);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Editar Artículo");

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
                    startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                }
            }
        };


        progressDialog = new ProgressDialog(this);
        etTituloArticulo = (EditText) findViewById(R.id.etEditarTituloArticulo);
        etDecripcionArticulo = (EditText) findViewById(R.id.etEditarDecripcionArticulo);

        tvNombrePDf = (TextView) findViewById(R.id.tvEditarNombrePDf);

        btnAgregarImagen = (ImageButton) findViewById(R.id.btnEditarAgregarImagen);
        btnCrearArticulo = (Button) findViewById(R.id.btnEditarArticulo);
        btnCancelarRedaccion = (Button) findViewById(R.id.btnCancelarEdicion);
        btnAdicionarPDF = (Button) findViewById(R.id.btnEditarAdicionarPDF);





        btnAgregarImagen.setOnClickListener(this);
        btnCrearArticulo.setOnClickListener(this);
        btnCancelarRedaccion.setOnClickListener(this);
        btnAdicionarPDF.setOnClickListener(this);

        if (getIntent().getSerializableExtra("articulo")!=null){
            articulo = (Articulo) getIntent().getSerializableExtra("articulo");

            etTituloArticulo.setText(articulo.getTitulo());
            etDecripcionArticulo.setText(articulo.getDecripcion());
            setImageArticulo(articulo.getImagen());
            btnAgregarImagen.setAdjustViewBounds(true);
            btnAgregarImagen.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditarAgregarImagen:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST);
                break;
            case R.id.btnEditarArticulo:
                registrarArticulo();
                break;
            case R.id.btnCancelarEdicion:
                finish();
                break;
            case R.id.btnEditarAdicionarPDF:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("application/pdf");

                try {
                    startActivityForResult(intent2, PICKFILE_REQUEST_CODE);
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Necesita una aplicacion para ver documentos pdf.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void registrarArticulo() {

        progressDialog.setMessage("Registrando Articulo...");
        final String titulo = etTituloArticulo.getText().toString().trim();
        final String descripcion = etDecripcionArticulo.getText().toString().trim();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        //VAlidar Datos!!!!
        progressDialog.show();
        if (imagenUri != null){
            StorageReference ruta = storageReference.child("Imagenes_Articulos").child(articulo.getId_articulo());
            ruta.putFile(imagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    if (pdfUri!=null){
                        StorageReference rutaPDF = storageReference.child("PDF_Articulos").child(articulo.getId_articulo());
                        rutaPDF.putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot2) {
                                Uri pdfDownloadUrl = taskSnapshot2.getDownloadUrl();


                                databaseReferenceArticulo.child(articulo.getId_articulo()).child("titulo").setValue(titulo);
                                databaseReferenceArticulo.child(articulo.getId_articulo()).child("decripcion").setValue(descripcion);
                                databaseReferenceArticulo.child(articulo.getId_articulo()).child("imagen").setValue(downloadUrl.toString());
                                databaseReferenceArticulo.child(articulo.getId_articulo()).child("id_usuario").setValue(user.getUid().toString());
                                databaseReferenceArticulo.child(articulo.getId_articulo()).child("id_articulo").setValue(articulo.getId_articulo());
                                databaseReferenceArticulo.child(articulo.getId_articulo()).child("pdf").setValue(pdfDownloadUrl.toString());
                                progressDialog.dismiss();
                                finish();
                                Toast.makeText(getApplicationContext(),"Se editó el articulo",Toast.LENGTH_LONG).show();

                            }
                        });
                    }else {

                        databaseReferenceArticulo.child(articulo.getId_articulo()).child("titulo").setValue(titulo);
                        databaseReferenceArticulo.child(articulo.getId_articulo()).child("decripcion").setValue(descripcion);
                        databaseReferenceArticulo.child(articulo.getId_articulo()).child("imagen").setValue(downloadUrl.toString());
                        databaseReferenceArticulo.child(articulo.getId_articulo()).child("id_usuario").setValue(user.getUid().toString());
                        databaseReferenceArticulo.child(articulo.getId_articulo()).child("id_articulo").setValue(articulo.getId_articulo());
                        progressDialog.dismiss();
                        finish();
                        Toast.makeText(getApplicationContext(),"Se editó el articulo",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            databaseReferenceArticulo.child(articulo.getId_articulo()).child("titulo").setValue(titulo);
            databaseReferenceArticulo.child(articulo.getId_articulo()).child("decripcion").setValue(descripcion);
            databaseReferenceArticulo.child(articulo.getId_articulo()).child("id_usuario").setValue(user.getUid().toString());
            databaseReferenceArticulo.child(articulo.getId_articulo()).child("id_articulo").setValue(articulo.getId_articulo());
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Se editó el articulo",Toast.LENGTH_LONG).show();
            finish();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK && data != null) {
                    imagenUri = data.getData();
                    btnAgregarImagen.setImageURI(imagenUri);
                    btnAgregarImagen.setAdjustViewBounds(true);
                    btnAgregarImagen.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
                }
                break;
            case PICKFILE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null){
                    if (!data.getData().getLastPathSegment().endsWith(".pdf")){
                        Toast.makeText(getApplicationContext(),"Debe seleccionar un documento PDF",Toast.LENGTH_LONG).show();
                    }else{
                        pdfUri = data.getData();
                        tvNombrePDf.setText(pdfUri.getLastPathSegment());
                        tvNombrePDf.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Se agrego un PDF al artículo.",Toast.LENGTH_LONG).show();
                    }
                    break;
                }

        }
    }


    public void setImageArticulo(final String image){


        Picasso.with(getApplicationContext()).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(btnAgregarImagen, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(getApplicationContext()).load(image).into(btnAgregarImagen);
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
