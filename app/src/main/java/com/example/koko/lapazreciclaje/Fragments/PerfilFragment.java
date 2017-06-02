package com.example.koko.lapazreciclaje.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.koko.lapazreciclaje.Activities.LogInActivity;
import com.example.koko.lapazreciclaje.Activities.NavigationActivity;
import com.example.koko.lapazreciclaje.Activities.RedactarArticuloActivity;
import com.example.koko.lapazreciclaje.Adapters.ArticulosAdapter;
import com.example.koko.lapazreciclaje.Adapters.ArticulosUsuarioAdapter;
import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.example.koko.lapazreciclaje.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceArticulos;

    private RecyclerView rvListaArticulos;
    private List<Articulo> listaArticulos;
    private ArticulosUsuarioAdapter articulosUsuarioAdapter;
    private Query query;

    private View view;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_perfil, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(false).setMessage("Usted no inicio sesion. ¿Desea Iniciar sesión o crear una cuenta?");
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getContext(),LogInActivity.class));
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getContext(),NavigationActivity.class));
                            getActivity().finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{

                    query = firebaseDatabase.getReference().child("articulo").orderByChild("id_usuario").equalTo(firebaseAuth.getCurrentUser().getUid().toString());
                    query.keepSynced(true);
                    mostrarArticulosUsuario();
                }
            }
        };
        rvListaArticulos = (RecyclerView) view.findViewById(R.id.rvListaArticulosusuario);
        rvListaArticulos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaArticulos = new ArrayList<>();
        articulosUsuarioAdapter = new ArticulosUsuarioAdapter(listaArticulos,getContext());
        rvListaArticulos.setAdapter(articulosUsuarioAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

        if (query != null){
            mostrarArticulosUsuario();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_perfil_usuario,menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.salir) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(),NavigationActivity.class));
            getActivity().finish();
            return true;
        }
        if (id == R.id.adicionar) {
            startActivity(new Intent(getContext(),RedactarArticuloActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarArticulosUsuario() {
        listaArticulos.clear();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Articulo articulo = dataSnapshot.getValue(Articulo.class);

                Boolean w = true;
                for (Articulo art :
                        listaArticulos) {
                    if (articulo.getId_articulo().equals(art.getId_articulo())){
                        w= false;
                    }
                }
                if (w){
                    listaArticulos.add(articulo);
                    articulosUsuarioAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                mostrarArticulosUsuario();
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
