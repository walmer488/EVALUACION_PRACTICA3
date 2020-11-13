package com.example.crudmysql_eva3.ui.ListaProductos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudmysql_eva3.AdaptadorCategorias;
import com.example.crudmysql_eva3.AdaptadorProductos;
import com.example.crudmysql_eva3.ModeloCategoria;
import com.example.crudmysql_eva3.ModeloProducto;
import com.example.crudmysql_eva3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListaProductos extends Fragment {

    private RecyclerView RV_lista;
    AdaptadorProductos adaptador;
    public static final String URL_buscarProductos = "http://192.168.1.99/AppInventario/buscar_producto.php";
    List<ModeloProducto> ListaProductos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_lista_productos, container, false);


        RV_lista = root.findViewById(R.id.RV_lista);
        RV_lista.setHasFixedSize(true);
        RV_lista.setLayoutManager(new LinearLayoutManager(this.getContext()));


        ListaProductos = new ArrayList<>();

        loadProducto();

        return root;
    }

    private void loadProducto(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_buscarProductos,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject productos = array.getJSONObject(i);

                                ListaProductos.add(new ModeloProducto(
                                        productos.getInt("id_producto"),
                                        productos.getString("nom_producto"),
                                        productos.getString("des_producto"),
                                        productos.getInt("stock"),
                                        productos.getDouble("precio"),
                                        productos.getString("unidad_medida"),
                                        productos.getInt("estado_producto"),
                                        productos.getInt("categoria")

                                ));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AdaptadorProductos adaptadorProductos = new AdaptadorProductos( getContext(), ListaProductos );
                        RV_lista.setAdapter(adaptadorProductos);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


}