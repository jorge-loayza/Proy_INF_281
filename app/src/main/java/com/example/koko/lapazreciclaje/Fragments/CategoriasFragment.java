package com.example.koko.lapazreciclaje.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.koko.lapazreciclaje.Activities.CategoriaActivity;
import com.example.koko.lapazreciclaje.Activities.LogInActivity;
import com.example.koko.lapazreciclaje.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Intent intent;
    private Button btnPapel,btnPlasticos,btnVidrio,btnDesechosPeligrosos,btnDesechosOrganicos,btnRestoResiduos,btnIniciarSesion,btnCrearCuenta;

    public CategoriasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.view = inflater.inflate(R.layout.fragment_categorias, container, false);

        btnPapel = (Button) view.findViewById(R.id.btnPapelCarton);
        btnPlasticos = (Button) view.findViewById(R.id.btnPlasticosLatas);
        btnVidrio = (Button) view.findViewById(R.id.btnVidrio);
        btnDesechosPeligrosos = (Button) view.findViewById(R.id.btnDesechosPeligrosos);
        btnDesechosOrganicos = (Button) view.findViewById(R.id.btnDesechosOrganicos);
        btnRestoResiduos = (Button) view.findViewById(R.id.btnRestosResiduos);



        btnPapel.setOnClickListener(this);
        btnPlasticos.setOnClickListener(this);
        btnVidrio.setOnClickListener(this);
        btnDesechosPeligrosos.setOnClickListener(this);
        btnDesechosOrganicos.setOnClickListener(this);
        btnRestoResiduos.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPapelCarton :
                intent = new Intent(getContext(),CategoriaActivity.class);
                intent.putExtra("categoria","1");
                startActivity(intent);
                break;
            case R.id.btnPlasticosLatas :
                intent = new Intent(getContext(),CategoriaActivity.class);
                intent.putExtra("categoria","2");
                startActivity(intent);
                break;
            case R.id.btnVidrio :
                intent = new Intent(getContext(),CategoriaActivity.class);
                intent.putExtra("categoria","3");
                startActivity(intent);
                break;
            case R.id.btnDesechosPeligrosos :
                intent = new Intent(getContext(),CategoriaActivity.class);
                intent.putExtra("categoria","4");
                startActivity(intent);
                break;
            case R.id.btnDesechosOrganicos :
                intent = new Intent(getContext(),CategoriaActivity.class);
                intent.putExtra("categoria","5");
                startActivity(intent);
                break;
            case R.id.btnRestosResiduos :
                intent = new Intent(getContext(),CategoriaActivity.class);
                intent.putExtra("categoria","6");
                startActivity(intent);
                break;
            case R.id.btnIniciarSesion:
                intent = new Intent(getContext(),LogInActivity.class);
                startActivity(intent);
                break;

        }
    }
}
