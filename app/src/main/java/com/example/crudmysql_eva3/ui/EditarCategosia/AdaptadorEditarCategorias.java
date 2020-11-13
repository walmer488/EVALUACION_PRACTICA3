package com.example.crudmysql_eva3.ui.EditarCategosia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudmysql_eva3.ModeloCategoria;
import com.example.crudmysql_eva3.R;

import java.util.List;

public class AdaptadorEditarCategorias extends RecyclerView.Adapter<AdaptadorEditarCategorias.ViewHolder> {


    private Context mctx;
    private List<ModeloCategoria> CategoriaLista;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_idCat, tv_NombreCat, tv_Estado_Cat;
        Button btneditarCat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_idCat = itemView.findViewById(R.id.et_idcategoria);
            tv_NombreCat = itemView.findViewById(R.id.et_namecategoria);
            tv_Estado_Cat = itemView.findViewById(R.id.sp_estado);

        }
    }

    public AdaptadorEditarCategorias(Context mctx ,List<ModeloCategoria> categoriaLista) {
        this.CategoriaLista = categoriaLista;
        this.mctx = mctx;
    }

    @NonNull
    @Override
    public AdaptadorEditarCategorias.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_editar_categoria, viewGroup, false);
        AdaptadorEditarCategorias.ViewHolder viewHolder = new AdaptadorEditarCategorias.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEditarCategorias.ViewHolder categoriasViewHolder, int i) {

        categoriasViewHolder.tv_idCat.setText(String.valueOf(CategoriaLista.get(i).getId_categoria()));
        categoriasViewHolder.tv_NombreCat.setText(CategoriaLista.get(i).getNombre_categoria());
        categoriasViewHolder.tv_Estado_Cat.setText(String.valueOf(CategoriaLista.get(i).getEstado_categoria()));
    }

    @Override
    public int getItemCount() {
        return CategoriaLista.size();
    }

}
