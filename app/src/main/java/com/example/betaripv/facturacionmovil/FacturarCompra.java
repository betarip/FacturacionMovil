package com.example.betaripv.facturacionmovil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.betaripv.facturacionmovil.utilerias.Colores;
import com.example.betaripv.facturacionmovil.utilerias.ServicioWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FacturarCompra extends AppCompatActivity {
    public static final String TAG = FacturarCompra.class.getSimpleName();
    public static final String ID_COMPRA = "com.example.betaripv.facturacionmovil.compra.ID";
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";

    ProgressDialog pDialog;

    final Context context = this;
    private Franquicia itemDetallado;
    private Compra compraEncontrada;
    private View view;
    private Button buscarCliente, mostrarCliente, mostrarCompra, facturar;
    private EditText rfcCliente;
    private String colorTexto;
    private String colorFondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facturar_compra);
        buscarCliente = (Button) findViewById(R.id.btnBusCliente);
        mostrarCliente = (Button) findViewById(R.id.btnDeCli);
        mostrarCompra = (Button) findViewById(R.id.btnDeCom);
        facturar = (Button) findViewById(R.id.btnDeFac);
        rfcCliente = (EditText) findViewById(R.id.txtInCliente);
        itemDetallado = Franquicia.getItem(getIntent().getIntExtra(ActividadDetalle.ID_FRANQUICIA, 0));
        compraEncontrada = Compra.getCompraSelect();
        view = findViewById(R.id.principal);
        //SET Colors
        colorTexto = Colores.textColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        colorFondo = Colores.backgroundColor(Color.parseColor(itemDetallado.getColorSecundario()));
        view.setBackgroundColor(Color.parseColor(colorFondo));
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(itemDetallado.getColorPrincipalDark());
        }

        Colores.setColoresBtn(buscarCliente,itemDetallado);
        Colores.setColoresBtn(mostrarCliente,itemDetallado);
        Colores.setColoresBtn(mostrarCompra,itemDetallado);
        Colores.setColoresBtn(facturar,itemDetallado);

        Colores.setColoresEdit(rfcCliente,itemDetallado,colorFondo);


        usarToolbar();

    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        toolbar.setTitleTextColor(Color.parseColor(colorTexto));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Datos de Factura");


    }



    public void buscarCliente(View view){
        String rfc = rfcCliente.getText().toString();
        if(!rfc.equals("") ) {
            peticion(ServicioWeb.urlBase + ServicioWeb.CLIENTE, rfc);
        }else{
            String mensaje;
            if(rfc.equals("")){
                mensaje = "Se debe introducir un ITU";
            }else{
                mensaje ="Numero de ITU incompleto";
            }
            Toast.makeText(this,mensaje, Toast.LENGTH_LONG);
        }

    }


    private void peticion(String url, final String... par) {
        Log.d(TAG, "*******************    url     *****************************");
        Log.d(TAG, url);
        RequestQueue queue = Volley.newRequestQueue(context);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Buscando cliente...");
        pDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "*******************    Response     *****************************");
                            Log.d(TAG, response.toString());
                            JSONObject jsonResponse = new JSONObject(response.toString());
                            if (jsonResponse.getInt("exito") == 1) {


                            } else {
                                Log.d(TAG, "*******************    Error     *****************************");
                                Log.d(TAG, "" + jsonResponse.getString("error"));
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
                        error.printStackTrace();

                        pDialog.hide();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("rfc", par[0]);
                return params;
            }
        };

        queue.add(postRequest);
    }


}
