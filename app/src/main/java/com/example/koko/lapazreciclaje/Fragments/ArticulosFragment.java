package com.example.koko.lapazreciclaje.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.koko.lapazreciclaje.Adapters.ArticulosAdapter;
import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.example.koko.lapazreciclaje.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticulosFragment extends Fragment {

    private View view;
    private RecyclerView rvlistaArticulos;
    private List<Articulo> listaArticulos;
    private DatabaseReference databaseReferenceArticulos;
    private ArticulosAdapter articulosAdapter;

    public ArticulosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_articulos, container, false);

        rvlistaArticulos = (RecyclerView) view.findViewById(R.id.rvListaArticulos);
        rvlistaArticulos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaArticulos = new ArrayList<>();
        articulosAdapter = new ArticulosAdapter(listaArticulos,getContext());
        rvlistaArticulos.setAdapter(articulosAdapter);
        databaseReferenceArticulos = FirebaseDatabase.getInstance().getReference().child("articulo");



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mostrarArticulos();


    }

    private void mostrarArticulos() {
        listaArticulos.clear();
        databaseReferenceArticulos.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Articulo articulo = dataSnapshot.getValue(Articulo.class);

                Boolean w = true;
                for (Articulo con :
                        listaArticulos) {
                    if (con.getId_usuario().equals(articulo.getId_usuario())){
                        w= false;
                    }
                }
                if (w){
                    listaArticulos.add(articulo);
                    articulosAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mostrarArticulos();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
