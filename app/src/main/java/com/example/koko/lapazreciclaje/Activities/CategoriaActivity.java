package com.example.koko.lapazreciclaje.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koko.lapazreciclaje.R;

public class CategoriaActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTitulo,tvDescripcion;
    private ImageView ivDescripcion;
    private Button btnLugares;
    private int categoria;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);


        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        ivDescripcion = (ImageView) findViewById(R.id.ivDescripcion);
        btnLugares = (Button) findViewById(R.id.btnLugares);
        btnLugares.setOnClickListener(this);

        categoria = getIntent().getIntExtra("categoria",1);

        switch (categoria){
            case 1:
                tvTitulo.setText(R.string.titulo_papelcarton);
                tvDescripcion.setText(R.string.descripcionPepelCarton);
                ivDescripcion.setImageResource(R.drawable.papel_carton);
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

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.btnLugares:
             Log.i("cat","click");
             intent = new Intent(getApplicationContext(),LugaresActivity.class);
             intent.putExtra("categoria",categoria+"");
             startActivity(intent);
             break;
     }
    }
}
