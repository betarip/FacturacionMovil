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

    private void cargarDatos() {
        String url = "http://192.168.0.100/Tesis/obtenerFranquicias.php";

        //new ObtenerFranquiciasLocal().execute();
        new ObtenerFranquiciasServer().execute(url);
        if (Franquicia.listaFranquicias.size() == 0) {
            cargarDialog();
        }

    }

    private class ObtenerFranquiciasLocal extends AsyncTask<String, Void, ArrayList<Franquicia>> {
        ArrayList<Franquicia> lista = new ArrayList<Franquicia>();

        @Override
        protected ArrayList<Franquicia> doInBackground(String... params) {
            /*
            lista.add(new Franquicia("Franquicia 1", R.drawable.franquicia1));
            lista.add(new Franquicia("Franquicia 2", R.drawable.franquicia2));
            lista.add(new Franquicia("Franquicia 3", R.drawable.franquicia3));
            lista.add(new Franquicia("Franquicia 4", R.drawable.franquicia4));
            lista.add(new Franquicia("Franquicia 5", R.drawable.franquicia5));
            lista.add(new Franquicia("Franquicia 6", R.drawable.franquicia6));
            lista.add(new Franquicia("Franquicia 7", R.drawable.franquicia7));
            lista.add(new Franquicia("Franquicia 8", R.drawable.franquicia8));
            lista.add(new Franquicia("Franquicia 9", R.drawable.franquicia9));
            lista.add(new Franquicia("Franquicia 10", R.drawable.franquicia10));
            for(int i = 0; i< 5 ; i++){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            */
            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<Franquicia> franquicias) {
            Intent i = new Intent(Splash.this, actividad_principal.class);
            if (Franquicia.listaFranquicias.size() > 0) {
                Franquicia.listaFranquicias.clear();
            }
            Franquicia.listaFranquicias.addAll(franquicias);
            startActivity(i);
            finish();
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private class ObtenerFranquiciasServer extends AsyncTask<String, Void, ArrayList<Franquicia>> {
        ArrayList<Franquicia> lista = new ArrayList<Franquicia>();
        private Exception exception;

        @Override
        protected ArrayList<Franquicia> doInBackground(String... params) {


            try {
                Log.d(TAG, "*******************    Open Connection    *****************************");
                URL url = new URL(params[0]);
                Log.d(TAG, "Received URL:  " + url);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");

                /*
                JSONObject dato = new JSONObject();
                dato.put("var1", 10);
                dato.put("var2", 20);

                OutputStreamWriter wr =
                        new OutputStreamWriter(con.getOutputStream());
                wr.write(dato.toString());
                wr.flush();

                */


                Log.d(TAG, "Con Status: " + con);

                InputStream in = con.getInputStream();
                Log.d(TAG, "GetInputStream:  " + in);

                Log.d(TAG, "*******************    String Builder     *****************************");
                String line = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");

                }
                br.close();
                Log.d(TAG, "*******************    JSON Object     *****************************");
                JSONObject reader = new JSONObject(sb.toString());
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
                } else {
                    Log.d(TAG, "*******************    Error     *****************************");
                    Log.d(TAG, "" + reader.getString("error"));
                }


            } catch (Exception e) {
                this.exception = e;

                return null;
            }
            return lista;
        }

        @Override
        protected void onPostExecute(ArrayList<Franquicia> franquicias) {
            if (franquicias != null) {
                Intent i = new Intent(Splash.this, actividad_principal.class);
                if (Franquicia.listaFranquicias.size() > 0) {
                    Franquicia.listaFranquicias.clear();
                }
                Franquicia.listaFranquicias.addAll(franquicias);
                startActivity(i);
            }


            finish();
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

}
