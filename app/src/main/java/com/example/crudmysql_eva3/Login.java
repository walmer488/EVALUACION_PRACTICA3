package com.example.crudmysql_eva3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    private Button ini;
    private EditText ET_USU, ET_CLA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ini = findViewById(R.id.btninicio);
        ET_USU = findViewById(R.id.ET_USU);
        ET_CLA = findViewById(R.id.ET_CLA);


        ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread tr = new Thread(){
                    @Override
                    public void run() {
                        final String resultado = Ingresar(ET_USU.getText().toString(), ET_CLA.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r = obtenerDatosJson(resultado);
                                if (r > 0){
                                    Intent i = new Intent(Login.this , MainActivity.class);
                                    i.putExtra("usuario", ET_USU.getText().toString());
                                    startActivity(i);
                                    ET_USU.setText(null);
                                    ET_CLA.setText(null);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Usuario O Clave Incorrectos", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                };
                tr.start();
            }
        });
    }

    public String Ingresar(String usuario, String Clave){

        URL url = null;
        String linea = "";
        int respuesta=0;
        StringBuilder resul=null;

        try {

            url = new URL("http://192.168.1.99/AppInventario/validar_usuario.php?usuario="+usuario+"&clave="+Clave);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            respuesta = connection.getResponseCode();

            resul = new StringBuilder();

            if (respuesta==HttpURLConnection.HTTP_OK){

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((linea = reader.readLine()) !=null){
                    resul.append(linea);
                }
            }

        }catch (Exception e){

        }

        return resul.toString();
    }

    public int obtenerDatosJson(String response){
        int res = 0;

        try {

            JSONArray json = new JSONArray(response);

            if (json.length() > 0 ){
                res = 1;
            }

        }catch (Exception e){

        }

        return res;
    }

}