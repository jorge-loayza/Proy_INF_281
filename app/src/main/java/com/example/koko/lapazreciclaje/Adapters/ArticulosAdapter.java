package com.example.koko.lapazreciclaje.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.example.koko.lapazreciclaje.R;

import java.util.List;

/**
 * Created by koko on 18-05-17.
 */

public class ArticulosAdapter extends RecyclerView.Adapter<ArticulosAdapter.ArticulosViewHolder> {

    List<Articulo> articulos;


    @Override
    public ArticulosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ariticulo_row,parent,false);
        ArticulosViewHolder articulosViewHolder = new ArticulosViewHolder(view);
        return articulosViewHolder;
    }

    @Override
    public void onBindViewHolder(ArticulosViewHolder holder, int position) {
        Articulo articulo = articulos.get(position);
        holder.tvTituloArticulo.setText(articulo.getTitulo());
        holder.tvDescripcionArticulo.setText(articulo.getDecripcion());
    }

    @Override
    public int getItemCount() {
        return articulos.size();
    }

    public static class ArticulosViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTituloArticulo,tvDescripcionArticulo;
        private View view;

        public ArticulosViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public void setTitulo(String titulo){
            tvTituloArticulo = (TextView) view.findViewById(R.id.tvTituloArticulo);
            tvTituloArticulo.setText(titulo);
        }
        public void setDescripcion(String descripcion){
            tvDescripcionArticulo = (TextView) view.findViewById(R.id.tvDescripcionArticulo);
            tvDescripcionArticulo.setText(descripcion);
        }
    }
}
