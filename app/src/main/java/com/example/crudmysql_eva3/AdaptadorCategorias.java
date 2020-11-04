package com.example.crudmysql_eva3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {

    private Context mctx;
    private List<ModeloCategoria> CategoriaLista;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idCat, tv_NombreCat, tv_Estado_Cat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_idCat = itemView.findViewById(R.id.tv_idCat);
            tv_NombreCat = itemView.findViewById(R.id.tv_NombreCat);
            tv_Estado_Cat = itemView.findViewById(R.id.tv_EstadoCat);

        }
    }

    public AdaptadorCategorias(Context mctx ,List<ModeloCategoria> categoriaLista) {
        this.CategoriaLista = categoriaLista;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_reciclerview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder categoriasViewHolder, int i) {

        categoriasViewHolder.tv_idCat.setText(String.valueOf(CategoriaLista.get(i).getId_categoria()));
        categoriasViewHolder.tv_NombreCat.setText(CategoriaLista.get(i).getNombre_categoria());
        categoriasViewHolder.tv_Estado_Cat.setText(String.valueOf(CategoriaLista.get(i).getEstado_categoria()));
    }

    @Override
    public int getItemCount() {

        return CategoriaLista.size();
    }

    public void editar(){
        Bundle bundle = new Bundle();
    }
}
