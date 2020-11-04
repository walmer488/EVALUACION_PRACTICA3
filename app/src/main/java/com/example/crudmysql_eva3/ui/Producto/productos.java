package com.example.crudmysql_eva3.ui.Producto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.crudmysql_eva3.ModeloCategoria;
import com.example.crudmysql_eva3.ModeloProducto;
import com.example.crudmysql_eva3.MySingleton;
import com.example.crudmysql_eva3.R;
import com.example.crudmysql_eva3.Setting_VAR;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class productos extends Fragment {

    private TextInputLayout ti_id, ti_nombre_prod, ti_descripcion, ti_stock,
            ti_precio, ti_unidadmedida;
    private EditText et_id, et_nombre_prod, et_descripcion, et_stock,
            et_precio, et_unidadmedida;
    private Spinner sp_estadoProductos, sp_fk_categoria;
    private TextView tv_fechahora;
    private Button btnSave, btnNew;
    ProgressDialog progressDialog;
    ArrayList<String> lista = null;
    ArrayList<ModeloCategoria> listaCategorias;

    String elementos[] = {"Uno", "Dos", "Tres", "Cuatro", "Cinco"};
    final String[] elementos1 = new String[]{
            "Seleccione",
            "1",
            "2",
            "3",
            "4",
            "5"
    };
    String idcategoria = "";
    String nombrecategoria = "";
    int conta = 0;
    String datoStatusProduct = "";

    ModeloProducto dto = new ModeloProducto();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        ti_id = view.findViewById(R.id.ti_id);
        ti_nombre_prod = view.findViewById(R.id.ti_nombre_prod);
        ti_descripcion = view.findViewById(R.id.ti_descripcion);

        et_id = view.findViewById(R.id.et_id);
        et_nombre_prod = view.findViewById(R.id.et_nombre_prod);
        et_descripcion = view.findViewById(R.id.et_descripcion);
        et_stock = view.findViewById(R.id.et_stock);
        et_precio = view.findViewById(R.id.et_precio);
        et_unidadmedida = view.findViewById(R.id.et_unidadmedida);
        sp_estadoProductos = view.findViewById(R.id.sp_estadoProductos);
        sp_fk_categoria = view.findViewById(R.id.sp_fk_categoria);
        tv_fechahora = view.findViewById(R.id.tv_fechahora);
        tv_fechahora.setText(timedate());
        btnSave = view.findViewById(R.id.btnSave);
        btnNew = view.findViewById(R.id.btnNew);

        sp_estadoProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_estadoProductos.getSelectedItemPosition() > 0) {
                    datoStatusProduct =  sp_estadoProductos.getSelectedItem().toString();
                } else {
                    datoStatusProduct = "";
                }
                Toast.makeText(getContext(), "" + datoStatusProduct, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fk_categorias(getContext());

        sp_fk_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (conta >= 1 && sp_fk_categoria.getSelectedItemPosition() > 0) {
                    String item_spinner = sp_fk_categoria.getSelectedItem().toString();
                    String s[] = item_spinner.split("~");
                    idcategoria = s[0].trim();
                    nombrecategoria = s[1];
                    Toast toast = Toast.makeText(getContext(), "Id cat: " + idcategoria + "\n" + "Nombre Categoria: " + nombrecategoria, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    idcategoria = "";
                    nombrecategoria = "";
                }
                conta++;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = et_id.getText().toString();
                String nombre = et_nombre_prod.getText().toString();
                String descripcion = et_descripcion.getText().toString();
                String stock = et_stock.getText().toString();
                String precio = et_precio.getText().toString();
                String unidad = et_unidadmedida.getText().toString();
                if(id.length() == 0){
                    et_id.setError("Campo obligatorio.");
                }else if(nombre.length() == 0){
                    et_nombre_prod.setError("Campo obligatorio.");
                }else if(descripcion.length() == 0){
                    et_descripcion.setError("Campo obligatorio.");
                }else if(stock.length() == 0){
                    et_stock.setError("Campo obligatorio.");
                }else if(precio.length() == 0){
                    et_precio.setError("Campo obligatorio.");
                }else if(unidad.length() == 0){
                    et_unidadmedida.setError("Campo obligatorio.");
                }else if(sp_estadoProductos.getSelectedItemPosition() == 0){
                    Toast.makeText(getContext(), "Debe seleccionar el estado del producto.", Toast.LENGTH_SHORT).show();
                }else if(sp_fk_categoria.getSelectedItemPosition() > 0){
                    Toast.makeText(getContext(), "Good...", Toast.LENGTH_SHORT).show();
                    save_productos(getContext(), id, nombre, descripcion,
                            stock, precio, unidad, datoStatusProduct, idcategoria);
                }else{
                    Toast.makeText(getContext(), "Debe seleccionar la categoria.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_product();
            }
        });
        return view;
    }

    private String timedate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fecha = sdf.format(cal.getTime());
        return fecha;
    }


    public void fk_categorias(final Context context){
        listaCategorias = new ArrayList<ModeloCategoria>();
        lista = new ArrayList<String>();
        lista.add("Seleccione Categoria");
        String url = Setting_VAR.URL_consultaAllCategorias;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    int totalEncontrados = array.length();
                    // Toast.makeText(context, "Total:"+totalEncontrados, Toast.LENGTH_SHORT).show();

                    ModeloCategoria obj_categorias = null;

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject categoriasObject = array.getJSONObject(i);
                        int id_categoria = categoriasObject.getInt("id_categoria");
                        String nombre_categoria = categoriasObject.getString("nom_categoria");
                        int estado_categoria = Integer.parseInt(categoriasObject.getString("estado_categoria"));

                        obj_categorias = new  ModeloCategoria(id_categoria, nombre_categoria, estado_categoria);

                        listaCategorias.add(obj_categorias);

                        lista.add(listaCategorias.get(i).getId_categoria()+" ~ "+listaCategorias.get(i).getNombre_categoria());

                        ArrayAdapter<String> adaptador =new ArrayAdapter<String> (getContext(),android.R.layout.simple_spinner_item, lista);

                        sp_fk_categoria.setAdapter(adaptador);

                        Log.i("Id Categoria", String.valueOf(obj_categorias.getId_categoria()));
                        Log.i("Nombre Categoria", obj_categorias.getNombre_categoria());
                        Log.i("Estado Categoria", String.valueOf(obj_categorias.getEstado_categoria()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error. Compruebe su acceso a Internet.", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void save_productos(final Context context,
                                final String id_prod,
                                final String nom_prod,
                                final String des_prod,
                                final String stock,
                                final String precio,
                                final String uni_medida,
                                final String estado_prod,
                                final String categoria
    ){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Setting_VAR.URL_registrar_productos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject requestJSON = null;
                Log.i(TAG, "response:" + response);
                try {
                    requestJSON = new JSONObject(response.toString());
                    String estado = requestJSON.getString("estado");
                    String mensaje = requestJSON.getString("mensaje");
                    if(estado.equals("1")){
                        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }else if(estado.equals("2")){
                        Toast.makeText(context, ""+mensaje, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "No se puedo guardar. \n" +
                        "Intentelo más tarde.", Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("id_prod", id_prod);
                map.put("nom_prod", nom_prod);
                map.put("des_prod", des_prod);
                map.put("stock", stock);
                map.put("precio", precio);
                map.put("uni_medida", uni_medida);
                map.put("estado_prod", estado_prod);
                map.put("categoria", categoria);
                return map;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void new_product() {
        et_id.setText(null);
        et_nombre_prod.setText(null);
        et_descripcion.setText(null);
        et_stock.setText(null);
        et_precio.setText(null);
        et_unidadmedida.setText(null);
        sp_estadoProductos.setSelection(0);
        sp_fk_categoria.setSelection(0);
    }

    public ArrayList<String> obtenerListaCategorias() {
        //ArrayList<String> lista = new ArrayList<String>();
        lista = new ArrayList<String>();
        lista.add("Seleccione Categoria");
        for(int i=0;i<=listaCategorias.size();i++){
            lista.add(listaCategorias.get(i).getId_categoria()+" ~ "+listaCategorias.get(i).getNombre_categoria());
        }
        return lista;
    }


}