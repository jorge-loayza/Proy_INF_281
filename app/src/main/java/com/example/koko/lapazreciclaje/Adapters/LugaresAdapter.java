package com.example.koko.lapazreciclaje.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.koko.lapazreciclaje.Objetos.LugarReciclaje;
import com.example.koko.lapazreciclaje.R;

import java.util.List;

/**
 * Created by koko on 31-05-17.
 */

public class LugaresAdapter extends RecyclerView.Adapter<LugaresAdapter.LugaresViewHolder>{

    private List<LugarReciclaje> listaLugares;

    public LugaresAdapter(List<LugarReciclaje> listaLugares) {
        this.listaLugares = listaLugares;
    }

    @Override
    public LugaresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lugar_row,parent,false);
        LugaresViewHolder lugaresViewHolder = new LugaresViewHolder(view);
        return lugaresViewHolder;
    }

    @Override
    public void onBindViewHolder(LugaresViewHolder holder, int position) {
        LugarReciclaje lugarReciclaje = listaLugares.get(position);
        holder.setNombre(lugarReciclaje.getNombre());
        holder.setTvDireccionLugar(lugarReciclaje.getDireccion());
        holder.setLugarReciclaje(lugarReciclaje);
    }

    @Override
    public int getItemCount() {
        return listaLugares.size();
    }

    public static class LugaresViewHolder extends RecyclerView.ViewHolder{

        private View view;
        private TextView tvNombreLugar,tvDireccionLugar;
        private LugarReciclaje lugarReciclaje;

        public LugaresViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            tvNombreLugar = (TextView) view.findViewById(R.id.tvNombreLugar);
            tvDireccionLugar = (TextView) view.findViewById(R.id.tvDireccionLugar);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //"google.navigation:q=-16.499438,-68.121945&mode=w"
                    String lugar ="google.navigation:q="+lugarReciclaje.getLatitud()+","+lugarReciclaje.getLongitud();
                    Uri gmmIntentUri = Uri.parse(lugar);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    view.getContext().startActivity(mapIntent);
                }
            });
        }

        private void setNombre(String nombre){
            tvNombreLugar.setText(nombre);
        }
        private void setTvDireccionLugar(String direccion){
            tvNombreLugar.setText(direccion);
        }


        public void setLugarReciclaje(LugarReciclaje lugarReciclaje) {
            this.lugarReciclaje = lugarReciclaje;
        }
    }
}
