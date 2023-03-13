package com.yorguin.gremlins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    // Se definan objetos relacionados en la interfaz de usuario.
    private EditText etEmail, etClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se asocian los objetos a la interfaz
        etEmail = findViewById(R.id.etEmail);
        etClave = findViewById(R.id.etClave);
    }
    public void ingresarClick(View v) {
        boolean valClave = validaUser();
        if (valClave) {
            Intent desk = new Intent(this, DeskActivity.class);
            startActivity(desk);
        } else {
            CharSequence text = "Contraseña invalida, intente nuevamente";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    // Se valida el usuario pasando datos a la API

    private boolean validaUser() {
        // Variable con ruta de la API, se le adiciona el email requerido en el GET
        String url = "http://35.208.26.192/apigrem/public/grem/login/" + etEmail.getText().toString().trim();

        final boolean[] res = {false};

        // StringRequest para la petición a la API, se usa Librería Volley.
        StringRequest getReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            /*
                onResponse recibirá la respuesta de la API que se obtine del StringRequest al cual
                se le pasa el método, la url que contiene la ruta de la API y un Listener o escucha que
                recibirá la respuesta en forma <String>
            */
            @Override
            public void onResponse(String response) {
                /*
                    Se crea un objeto Json para administrar el response
                    para gestionar posibles errores de la respuesta, se
                    usa un try/catch
                */
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = ja.getJSONObject(0);
                    // Se obtiene del objeto la clave para compararla.
                    String jClave = jo.getString("clave");
                    String txtClave = etClave.getText().toString();
                    // Se compara la clave obtenida con la digitada.

                    if ( jClave.equals(txtClave)  ) {
                        res[0] = true;
                    } else {
                        res[0] = false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            /// Se implementa para administrar errores.
            @Override
            public void onErrorResponse(VolleyError error) {
                Context ctx = getApplicationContext();
                CharSequence text = "Error de conexión API";
                Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(getReq);

        return res[0];
    }

}