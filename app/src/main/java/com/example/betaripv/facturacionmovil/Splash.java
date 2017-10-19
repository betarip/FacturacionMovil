package com.example.betaripv.facturacionmovil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.betaripv.facturacionmovil.clases.Franquicia;
import com.example.betaripv.facturacionmovil.utilerias.ServicioWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Splash extends AppCompatActivity {

    public static final String TAG = Splash.class.getSimpleName();
    ProgressDialog pDialog;
    //public String urlBase = "http://192.168.0.100/Tesis/";
    //public String urlBase ="http://pueblaroja.mx/pruebas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //cargarDatos();
        peticionVolley(ServicioWeb.urlBase + ServicioWeb.FRANQUICIAS);
        //

    }


    private void peticionVolley(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final ArrayList<Franquicia> lista = new ArrayList<Franquicia>();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Obteniendo franquicias...");
        pDialog.show();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        JSONObject reader = null;
                        try {
                            reader = new JSONObject(response.toString());
                            if (reader.getInt("exito") == 1) {
                                JSONArray franquicias = reader.getJSONArray("datos");
                                for (int i = 0; i < franquicias.length(); i++) {
                                    JSONObject f = franquicias.getJSONObject(i);
                                    Franquicia franq = new Franquicia(f.getString("id_franquicia"));
                                    franq.setRfc(f.getString("rfc"));
                                    franq.setNombre(f.getString("razon_nombre"));
                                    franq.setImagen(f.getString("image"));
                                    franq.setColorPrincipal(f.getString("color_principal"));
                                    franq.setColorSecundario(f.getString("color_secundario"));
                                    lista.add(franq);
                                }
                                Intent i = new Intent(Splash.this, actividad_principal.class);
                                if (Franquicia.listaFranquicias.size() > 0) {
                                    Franquicia.listaFranquicias.clear();
                                }
                                Franquicia.listaFranquicias.addAll(lista);
                                startActivity(i);
                            } else {
                                Log.d(TAG, "*******************    Error     *****************************");
                                Log.d(TAG, "" + reader.getString("error"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        pDialog.hide();
                        cargarDialog();
                    }
                }
        );

        queue.add(getRequest);

    }

    private void cargarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Error al conectar con el servicio");
        builder.setPositiveButton("Re - Intentar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //urlBase = "http://192.168.0.101/Tesis/";
                peticionVolley(ServicioWeb.urlBase + ServicioWeb.FRANQUICIAS);
            }
        });
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }







}
