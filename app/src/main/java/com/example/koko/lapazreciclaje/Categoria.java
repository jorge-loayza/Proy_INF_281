package com.example.koko.lapazreciclaje;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class Categoria extends AppCompatActivity {

    private TextView tvTitulo,tvDescripcion;
    private ImageView ivDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);


        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        ivDescripcion = (ImageView) findViewById(R.id.ivDescripcion);

        int categoria = getIntent().getIntExtra("categoria",1);
        switch (categoria){
            case 1:
                tvTitulo.setText(R.string.titulo_papelcarton);
                tvDescripcion.setText(R.string.descripcionPepelCarton);
                break;
            case 2:
                tvTitulo.setText(R.string.titulo_plasticoslatas);
                tvDescripcion.setText(R.string.descripcionPlasticosLatas);
                ivDescripcion.setImageResource(R.drawable.plasticos_latas);
                break;
            case 3:
                tvTitulo.setText(R.string.titulo_vidrio);
                tvDescripcion.setText(R.string.descripcionVidrio);
                ivDescripcion.setImageResource(R.drawable.vidrio);
                break;
            case 4:
                tvTitulo.setText(R.string.titulo_desechosPeligrosos);
                tvDescripcion.setText(R.string.descripcionDesechosPeligrosos);
                ivDescripcion.setImageResource(R.drawable.desechos_peligrosos);
                break;
            case 5:
                tvTitulo.setText(R.string.titulo_desechosOrganicos);
                tvDescripcion.setText(R.string.descripcionDesechosOrganicos);
                ivDescripcion.setImageResource(R.drawable.desechos_organicos);
                break;
            case 6:
                tvTitulo.setText(R.string.titulo_restoResiduos);
                tvDescripcion.setText(R.string.descripcionRestoResiduos);
                ivDescripcion.setImageResource(R.drawable.resto_residuos);
                break;

        }

    }
}
