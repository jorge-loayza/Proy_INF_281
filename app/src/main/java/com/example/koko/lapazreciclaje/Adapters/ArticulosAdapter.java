package com.example.koko.lapazreciclaje.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koko.lapazreciclaje.Objetos.Articulo;
import com.example.koko.lapazreciclaje.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by koko on 18-05-17.
 */

public class ArticulosAdapter extends RecyclerView.Adapter<ArticulosAdapter.ArticulosViewHolder> {

    List<Articulo> articulos;

    Context context;

    public ArticulosAdapter(List<Articulo> articulos, Context context) {
        this.articulos = articulos;
        this.context = context;
    }

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
        holder.setImage(context,articulo.getImagen());
    }

    @Override
    public int getItemCount() {
        return articulos.size();
    }

    public static class ArticulosViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTituloArticulo,tvDescripcionArticulo;
        private ImageView articuloImagen;
        private View view;

        public ArticulosViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            tvTituloArticulo = (TextView) view.findViewById(R.id.tvTituloArticulo);
            tvDescripcionArticulo = (TextView) view.findViewById(R.id.tvDescripcionArticulo);
            articuloImagen = (ImageView) view.findViewById(R.id.ivArticulo);

        }

        public void setTitulo(String titulo){

            tvTituloArticulo.setText(titulo);
        }
        public void setDescripcion(String descripcion){

            tvDescripcionArticulo.setText(descripcion);
        }
        public void setImage(final Context ctx, final String image){


            //Picasso.with(ctx).load(image).into(tarjetsImagen);

            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(articuloImagen, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(articuloImagen);
                }
            });

        }
    }
}
