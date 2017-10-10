package com.example.betaripv.facturacionmovil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.betaripv.facturacionmovil.clases.Compra;
import com.example.betaripv.facturacionmovil.clases.Franquicia;
import com.example.betaripv.facturacionmovil.utilerias.Colores;
import com.example.betaripv.facturacionmovil.utilerias.Extras;
import com.example.betaripv.facturacionmovil.utilerias.ServicioWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActividadDetalle extends AppCompatActivity {
    public static final String TAG = ActividadDetalle.class.getSimpleName();


    ProgressDialog pDialog;

    final Context context = this;
    private TextView letrero;
    private Franquicia itemDetallado;
    private ImageView imagenExtendida;
    private TextView nombreFranquicia;
    private LinearLayout view;
    private String colorTexto;
    private String colorFondo;
    private Button btnBuscar, btnDetalles;
    private EditText itu;

    private TextInputLayout layoutITU;

    Animation anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalle);
        view = (LinearLayout) findViewById(R.id.activityDetalle);


        itemDetallado = Franquicia.getItem(getIntent().getIntExtra(Extras.ID_FRANQUICIA, 0));
        colorFondo = Colores.backgroundColor(Color.parseColor(itemDetallado.getColorSecundario()));


        view.setBackgroundColor(Color.parseColor(colorFondo));
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnDetalles = (Button) findViewById(R.id.btnDetalles);
        itu = (EditText) findViewById(R.id.textITU);
        layoutITU = (TextInputLayout) findViewById(R.id.layout_textITU);
        //anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        colorTexto = Colores.textColor(Color.parseColor(itemDetallado.getColorPrincipal()));

        Colores.setColoresLayout(layoutITU,itemDetallado,colorFondo);
        Colores.setColoresBtn(btnBuscar, itemDetallado);
        Colores.setColoresBtn(btnDetalles, itemDetallado);
        Colores.setColoresEdit(itu,itemDetallado,colorFondo);
        //SET colores de la franquicia
        //layoutITU.setHintTextAppearance(Color.parseColor(colorTexto));


        usarToolbar();

        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(itemDetallado.getColorPrincipalDark());
        }

        cargarDatosFranquicia();
    }

    private boolean revisarITU(){
        String numITU = itu.getText().toString().trim();
        if(numITU.isEmpty() || numITU.length() != 20 ){
            layoutITU.setErrorEnabled(true);
            layoutITU.setError("ITU invalido");

            return false;
        }else{
            layoutITU.setErrorEnabled(false);
            return true;
        }
    }

    private void cargarDatosFranquicia() {
        nombreFranquicia = (TextView) findViewById(R.id.nombre_franquicia);
        imagenExtendida = (ImageView) findViewById(R.id.imagen_extendida);
        byte[] decodeImage = Base64.decode(itemDetallado.getImagen().getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
        imagenExtendida.setImageBitmap(decodedByte);
        nombreFranquicia.setText(itemDetallado.getNombre());
        nombreFranquicia.setTextColor(Color.parseColor(Colores.textColor(Color.parseColor(colorFondo))));
    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        toolbar.setTitleTextColor(Color.parseColor(colorTexto));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Facturar Compra");
    }

    /*
    *
    * Este metodo activa la funcion del boton detalles
    * */
    public void mostrarDetallesFranquicia(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detalles_franquicia, null);
        alertDialog.setView(dialogView);
        TextView textViewRfc = (TextView) dialogView.findViewById(R.id.rfc);
        //TextView textViewNombreRazon = (TextView) dialogView.findViewById(R.id.nombre_franquicia);
        textViewRfc.setText(itemDetallado.getNombre());
        ImageView imagenDialog= (ImageView) findViewById(R.id.imagenFranquiciaDialog);
        byte[] decodeImage = Base64.decode(itemDetallado.getImagen().getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    /*
    * Este metodo busca la compra por el numero de ITU
    *
    * */

    public void buscarCompra(View view) {
        //String urlBase = "http://192.168.0.100/Tesis";
        //String urlBase ="http://pueblaroja.mx/pruebas";
        //String url = "/WebService/buscarCompra.php";
        String numeroITU = itu.getText().toString();
        if(revisarITU()){
            peticion(ServicioWeb.urlBase + ServicioWeb.COMPRA, numeroITU);
            hideSoftKeyboard();
        }
        /*

        if(!numeroITU.equals("") && numeroITU.length() == 20) {
            peticion(ServicioWeb.urlBase + ServicioWeb.COMPRA, numeroITU);
        }else{
            String mensaje;
            if(numeroITU.equals("")){
                mensaje = "Se debe introducir un ITU";
            }else{
                mensaje ="Numero de ITU incompleto";
            }
            Toast.makeText(this,mensaje, Toast.LENGTH_LONG);
        }
        */
    }

    private void peticion(String url, final String... par) {
        Log.d(TAG, "*******************    url     *****************************");
        Log.d(TAG, url);
        RequestQueue queue = Volley.newRequestQueue(context);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Buscando compras...");
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
                                Intent intent;
                                JSONObject compra = jsonResponse.getJSONObject("datos");
                                String idCompra = compra.getString("id_compra");
                                String idFranquicia = compra.getString("id_franquicia");
                                //if (idFranquicia.equals(itemDetallado.getIdFranquicia())) {
                                    intent = new Intent(context, FacturarCompra.class);
                                    Compra c = new Compra(idCompra);
                                    c.setSubtotal(compra.getString("subtotal"));
                                    c.setTotal(compra.getString("total"));
                                    Compra.setCompraSelect(c);
                                intent.putExtra(Extras.ID_COMPRA, idCompra);
                                intent.putExtra(Extras.ID_FRANQUICIA, itemDetallado.getId());
                                    startActivity(intent);
                                /*
                                } else {
                                    Log.d(TAG, "" + idFranquicia + "//" + itemDetallado.getIdFranquicia());
                                    cargarDialog("No corresponde la franquicia con el ITU");
                                }
        */
                            } else {
                                Log.d(TAG, "*******************    Error     *****************************");
                                Log.d(TAG, "" + jsonResponse.getString("error"));
                                cargarDialog(jsonResponse.getString("error"));
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
                        cargarDialog("No se ha encontrado la compra");
                        pDialog.hide();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("compra", par[0]);
                return params;
            }
        };

        queue.add(postRequest);
    }

    private void cargarDialog(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("Re - Intentar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


}
