package com.example.koko.lapazreciclaje.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.example.koko.lapazreciclaje.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ArticuloActivity extends AppCompatActivity {

    private Articulo articulo;
    private ImageView ivImagenArticulo;
    private TextView tvTitArticulo,tvDesArticulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        articulo = (Articulo) getIntent().getSerializableExtra("articulo");
        ivImagenArticulo = (ImageView) findViewById(R.id.ivImagenArticulo);
        tvTitArticulo = (TextView) findViewById(R.id.tvTitArticulo);
        tvDesArticulo = (TextView) findViewById(R.id.tvDesArticulo);
        setImage(articulo.getImagen());
        tvTitArticulo.setText(articulo.getTitulo());
        tvDesArticulo.setText(articulo.getContenido());


    }

    public void setImage(final String image){


        //Picasso.with(ctx).load(image).into(tarjetsImagen);

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
}
