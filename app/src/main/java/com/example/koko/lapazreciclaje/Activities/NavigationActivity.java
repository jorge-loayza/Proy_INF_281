package com.example.koko.lapazreciclaje.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.koko.lapazreciclaje.Fragments.AcercaFragment;
import com.example.koko.lapazreciclaje.Fragments.ArticulosFragment;
import com.example.koko.lapazreciclaje.Fragments.CategoriasFragment;
import com.example.koko.lapazreciclaje.Fragments.PerfilFragment;
import com.example.koko.lapazreciclaje.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavigationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Fragment categoriasFragment,perfilFragment,articulosFragment,acercaFragment;

    private Boolean sw = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,categoriasFragment).commit();
                    return true;
                case R.id.navigation_articulos:
                    getSupportActionBar().setTitle("Articulos");
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,articulosFragment).commit();
                    return true;
                case R.id.navigation_perfil:
                    getSupportActionBar().setTitle("Perfil de Usuario");
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,perfilFragment).commit();
                    return true;
                case R.id.navigation_acerca:
                    getSupportActionBar().setTitle("Acerca");
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,acercaFragment).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        categoriasFragment = new CategoriasFragment();
        articulosFragment = new ArticulosFragment();
        perfilFragment = new PerfilFragment();
        acercaFragment = new AcercaFragment();



        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    //btnCrearCuenta.setVisibility(View.GONE);
                    //btnIniciarSesion.setText("Ir al Perfil");
                }
            }
        };

        if (!sw){
            getSupportFragmentManager().beginTransaction().replace(R.id.content,categoriasFragment).commit();
            sw = true;
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
