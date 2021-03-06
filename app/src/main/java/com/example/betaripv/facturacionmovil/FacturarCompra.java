package com.example.betaripv.facturacionmovil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.betaripv.facturacionmovil.clases.Cliente;
import com.example.betaripv.facturacionmovil.clases.Compra;
import com.example.betaripv.facturacionmovil.clases.Factura;
import com.example.betaripv.facturacionmovil.clases.Franquicia;
import com.example.betaripv.facturacionmovil.utilerias.Colores;
import com.example.betaripv.facturacionmovil.utilerias.Extras;
import com.example.betaripv.facturacionmovil.utilerias.ServicioWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FacturarCompra extends ActividadBase {
    public static final String TAG = FacturarCompra.class.getSimpleName();

    ProgressDialog pDialog;

    final Context context = this;
    private Franquicia itemDetallado;
    private Cliente clienteEnc;
    private Compra compraEncontrada;
    private View view;
    private Button buscarCliente, mostrarCompra, facturar;
    private EditText rfcCliente;
    private String colorTexto;
    private String colorFondo;
    private TextInputLayout rfcClienteLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facturar_compra);
        buscarCliente = (Button) findViewById(R.id.btnBusCliente);

        mostrarCompra = (Button) findViewById(R.id.btnDeCom);
        facturar = (Button) findViewById(R.id.btnDeFac);
        rfcCliente = (EditText) findViewById(R.id.txtInCliente);
        rfcClienteLayout = (TextInputLayout) findViewById(R.id.layout_txtInCliente);
        itemDetallado = Franquicia.getItem(getIntent().getIntExtra(Extras.ID_FRANQUICIA, 0));
        compraEncontrada = Compra.getCompraSelect();
        colocarDatosClientes();
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

        Colores.setColoresBtn(buscarCliente, itemDetallado);
        Colores.setColoresBtn(mostrarCompra, itemDetallado);
        Colores.setColoresBtn(facturar, itemDetallado);

        Colores.setColoresEdit(rfcCliente, itemDetallado, colorFondo);

        usarToolbar();

    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        Log.d(TAG, "Regresar del metodo de registro");
        colocarDatosClientes();

    }

    private void colocarDatosClientes() {
        clienteEnc = Cliente.getClienteSelec();
        if (clienteEnc != null) {
            rfcCliente.setText(clienteEnc.getRfc());

            facturar.setEnabled(true);
            //actualizarDatosDialog();
            //mostrarDatosCliente();
        } else {
            SharedPreferences pref = getSharedPreferences("SPCliente", Context.MODE_PRIVATE);
            if (pref.contains("cliente")) {
                String json = pref.getString("cliente", "");
                try {
                    JSONObject jsonResponse = new JSONObject(json.toString());
                    Cliente nuevo = Cliente.JsontToCliente(jsonResponse);
                    Cliente.clienteSelec = nuevo;
                    clienteEnc = Cliente.getClienteSelec();
                    rfcCliente.setText(clienteEnc.getRfc());
                    facturar.setEnabled(true);
                    //actualizarDatosDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        toolbar.setTitleTextColor(Color.parseColor(colorTexto));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Datos de Factura");


    }

    public void mostrarDatosCliente(View view) {
        colocarDatosClientes();
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detalles_cliente, null);
        alertDialog.setView(dialogView);
        TextView textViewRfc = (TextView) dialogView.findViewById(R.id.rfc);
        textViewRfc.setText(clienteEnc.getRfc());
        TextView textViewRazon = (TextView) dialogView.findViewById(R.id.razon_nombre);
        textViewRazon.setText(clienteEnc.getRazonNombre());

        TextView textViewCorreo = (TextView) dialogView.findViewById(R.id.correo);
        textViewCorreo.setText(clienteEnc.getCorreo());

        TextView textViewCalle = (TextView) dialogView.findViewById(R.id.calle);
        textViewCalle.setText(clienteEnc.getDomicilio().getCalle());

        TextView textViewInt = (TextView) dialogView.findViewById(R.id.num_int);
        textViewInt.setText(clienteEnc.getDomicilio().getInte());

        TextView textViewExt = (TextView) dialogView.findViewById(R.id.num_ext);
        textViewExt.setText(clienteEnc.getDomicilio().getExt());

        TextView textViewColonia = (TextView) dialogView.findViewById(R.id.colonia);
        textViewColonia.setText(clienteEnc.getDomicilio().getColonia());

        TextView textViewLocalidad = (TextView) dialogView.findViewById(R.id.localidad);
        textViewLocalidad.setText(clienteEnc.getDomicilio().getLocalidad());

        TextView textViewRef = (TextView) dialogView.findViewById(R.id.referencia);
        textViewRef.setText(clienteEnc.getDomicilio().getRef());

        TextView textViewMuni = (TextView) dialogView.findViewById(R.id.municipio);
        textViewMuni.setText(clienteEnc.getDomicilio().getMunicipio());

        TextView textViewEst = (TextView) dialogView.findViewById(R.id.estado);
        textViewEst.setText(clienteEnc.getDomicilio().getEstado());

        TextView textViewPais = (TextView) dialogView.findViewById(R.id.pais);
        textViewPais.setText(clienteEnc.getDomicilio().getPais());

        TextView textViewCp = (TextView) dialogView.findViewById(R.id.cp);
        textViewCp.setText(clienteEnc.getDomicilio().getCp());


        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();


    }

    public void buscarCliente(View view) {
        hideSoftKeyboard();
        String rfc = rfcCliente.getText().toString();
        if (!rfc.equals("") && rfc.length() == 13) {
            peticionCliente(ServicioWeb.urlBase + ServicioWeb.CLIENTE, rfc);
            rfcClienteLayout.setErrorEnabled(false);
        } else {
            String mensaje;
            if (rfc.equals("")) {
                mensaje = "Se debe introducir un rfc";
            } else {
                mensaje = "RFC incompleto";
            }
            rfcClienteLayout.setErrorEnabled(true);
            rfcClienteLayout.setError(mensaje);
        }

    }

    private void peticionCliente(String url, final String... par) {
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
                                JSONObject jsonCliente = jsonResponse.getJSONObject("datos");
                                Cliente nuevo = Cliente.JsontToCliente(jsonCliente);
                                Cliente.clienteSelec = nuevo;
                                clienteEnc = Cliente.getClienteSelec();
                                //Guardar datos de cliente
                                SharedPreferences pref = getSharedPreferences("SPCliente", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("cliente", jsonCliente.toString());
                                editor.commit();
                                //habilitar boton cliente y facturacion
                                facturar.setEnabled(true);
                                Toast toast = Toast.makeText(getApplicationContext(), "Cliente " + nuevo.getRfc() + " encontrado",
                                        Toast.LENGTH_SHORT);
                                colocarDatosClientes();
                                //toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                                toast.show();
                                //Log.d(TAG, "" + response.toString());
                            } else {
                                cargarDialog("No existe Cliente ¿Desea Registrarlo?",1);
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

    private void peticionFacturar(String url, final String... par) {
        Log.d(TAG, "*******************    url     *****************************");
        Log.d(TAG, url);
        RequestQueue queue = Volley.newRequestQueue(context);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Facturando...");
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
                                Factura f = new Factura();
                                f.setPdf(jsonResponse.getString("pdf"));
                                f.setXml(jsonResponse.getString("timbre"));
                                Factura.setFacturaSelect(f);
                                Intent intent;
                                intent = new Intent(context, VistaFactura.class);
                                intent.putExtra(Extras.ID_FRANQUICIA, itemDetallado.getId());
                                startActivity(intent);
                            } else {
                                cargarDialog("Error , ¿Desea re-intentar?",2);
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
                params.put("ITU", par[0]);
                params.put("cliente", par[1]);
                return params;
            }
        };

        queue.add(postRequest);
    }

    private void cargarDialog(String mensaje, int opcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        if(opcion == 1) {
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent;
                    intent = new Intent(context, RegistrarCliente.class);
                    intent.putExtra(Extras.ID_COMPRA, compraEncontrada.getID());
                    intent.putExtra(Extras.ID_FRANQUICIA, itemDetallado.getId());
                    intent.putExtra("clienteRegistrar", rfcCliente.getText().toString());
                    startActivity(intent);
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }else{
            builder.setPositiveButton("Re - intentar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String itu = Compra.getCompraSelect().getITU();
                    String idCliente = Cliente.getClienteSelec().getIdCliente();
                    peticionFacturar(ServicioWeb.urlBase + ServicioWeb.FACTURAR_JSON, itu, idCliente);
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }

    /*
    *
    * Este metodo activa la funcion del boton detalles
    * */
    public void mostrarDetallesCompra(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_detalles_compra, null);
        alertDialog.setView(dialogView);
        TextView textViewTotal = (TextView) dialogView.findViewById(R.id.total);
        textViewTotal.append(compraEncontrada.getTotal());

        TextView textViewSubtotal = (TextView) dialogView.findViewById(R.id.subtotal);
        textViewSubtotal.append(compraEncontrada.getSubtotal());


        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public void facturarCompra(View view) {
        String itu = Compra.getCompraSelect().getITU();
        String idCliente = Cliente.getClienteSelec().getIdCliente();
        peticionFacturar(ServicioWeb.urlBase + ServicioWeb.FACTURAR_JSON, itu, idCliente);
    }





}
