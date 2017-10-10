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
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.betaripv.facturacionmovil.clases.*;
import com.example.betaripv.facturacionmovil.clases.Franquicia;
import com.example.betaripv.facturacionmovil.utilerias.Colores;
import com.example.betaripv.facturacionmovil.utilerias.Extras;
import com.example.betaripv.facturacionmovil.utilerias.ServicioWeb;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarCliente extends AppCompatActivity {

    public static final String TAG = FacturarCompra.class.getSimpleName();
    ProgressDialog pDialog;
    private Cliente clienteEnc;
    private Compra compraEncontrada;

    final Context context = this;

    private EditText etRfcCliente, etNombreRazon, etCorreo, etCalle, etNumInt, etNumExt,
            etColonia, etLocalidad, etReferencia, etMunicipio, etEstado, etPais, etCp;

    private TextInputLayout tilRfcCliente, tilNombreRazon, tilCorreo, tilCalle, tilNumInt, tilNumExt,
            tilColonia, tilLocalidad, tilReferencia, tilMunicipio, tilEstado, tilPais, tilCp;

    private Button btnRegistar;
    private Franquicia itemDetallado;
    private View view;
    private String colorTexto, colorFondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cliente);

        itemDetallado = Franquicia.getItem(getIntent().getIntExtra(Extras.ID_FRANQUICIA, 0));
        compraEncontrada = Compra.getCompraSelect();


        etRfcCliente = (EditText) findViewById(R.id.textRFC);
        etRfcCliente.setText(getIntent().getStringExtra("clienteRegistrar"));
        tilRfcCliente = (TextInputLayout) findViewById(R.id.layout_textRFC);

        etNombreRazon = (EditText) findViewById(R.id.textNombre);
        tilNombreRazon = (TextInputLayout) findViewById(R.id.layout_textNombre);

        etCorreo = (EditText) findViewById(R.id.textCorreo);
        tilCorreo = (TextInputLayout) findViewById(R.id.layout_textCorreo);

        etCalle = (EditText) findViewById(R.id.textCalle);
        tilCalle = (TextInputLayout) findViewById(R.id.layout_textCalle);

        etNumInt = (EditText) findViewById(R.id.textNumInt);
        tilNumInt = (TextInputLayout) findViewById(R.id.layout_textNumInt);

        etNumExt = (EditText) findViewById(R.id.textNumExt);
        tilNumExt = (TextInputLayout) findViewById(R.id.layout_textNumExt);

        etColonia = (EditText) findViewById(R.id.textColonia);
        tilColonia = (TextInputLayout) findViewById(R.id.layout_textColonia);

        etLocalidad = (EditText) findViewById(R.id.textLocalidad);
        tilLocalidad = (TextInputLayout) findViewById(R.id.layout_textLocalidad);

        etReferencia = (EditText) findViewById(R.id.textRef);
        tilReferencia = (TextInputLayout) findViewById(R.id.layout_textRef);

        etMunicipio = (EditText) findViewById(R.id.textMun);
        tilMunicipio = (TextInputLayout) findViewById(R.id.layout_textMun);

        etEstado = (EditText) findViewById(R.id.textEsta);
        tilEstado = (TextInputLayout) findViewById(R.id.layout_textEsta);

        etPais = (EditText) findViewById(R.id.textPais);
        tilPais = (TextInputLayout) findViewById(R.id.layout_textPais);

        etCp = (EditText) findViewById(R.id.textCP);
        tilCp = (TextInputLayout) findViewById(R.id.layout_textCp);

        btnRegistar = (Button) findViewById(R.id.btnRegistar);

        view = findViewById(R.id.principal);

        debug();

        colorTexto = Colores.textColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        colorFondo = Colores.backgroundColor(Color.parseColor(itemDetallado.getColorSecundario()));
        view.setBackgroundColor(Color.parseColor(colorFondo));
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(itemDetallado.getColorPrincipalDark());
        }

        Colores.setColoresEdit(etRfcCliente, itemDetallado, colorFondo);
        Colores.setColoresEdit(etNombreRazon, itemDetallado, colorFondo);
        Colores.setColoresEdit(etCorreo, itemDetallado, colorFondo);
        Colores.setColoresEdit(etCalle, itemDetallado, colorFondo);
        Colores.setColoresEdit(etNumInt, itemDetallado, colorFondo);
        Colores.setColoresEdit(etNumExt, itemDetallado, colorFondo);
        Colores.setColoresEdit(etColonia, itemDetallado, colorFondo);
        Colores.setColoresEdit(etLocalidad, itemDetallado, colorFondo);
        Colores.setColoresEdit(etReferencia, itemDetallado, colorFondo);
        Colores.setColoresEdit(etMunicipio, itemDetallado, colorFondo);
        Colores.setColoresEdit(etEstado, itemDetallado, colorFondo);
        Colores.setColoresEdit(etPais, itemDetallado, colorFondo);
        Colores.setColoresEdit(etCp, itemDetallado, colorFondo);

        Colores.setColoresBtn(btnRegistar, itemDetallado);
        usarToolbar();


    }

    @Override
    protected void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(TAG, "Regresar del metodo de registro");


    }

    public void debug() {
        etNombreRazon.setText("Razon Social");
        etCorreo.setText("iva@nana.com");
        etCalle.setText("Calle");
        etNumExt.setText("Exterior");
        etNumInt.setText("Interior");
        etColonia.setText("Colonia");
        etLocalidad.setText("Localidad");
        etReferencia.setText("Referencia");
        etMunicipio.setText("Municipio");
        etEstado.setText("Estado");
        etPais.setText("Pais");
        etCp.setText("72000");


    }

    private boolean rfcValido() {
        if (!etRfcCliente.equals("") && etRfcCliente.length() == 13) {
            tilRfcCliente.setErrorEnabled(false);
            return true;
        } else {
            String mensaje;
            if (etRfcCliente.equals("")) {
                mensaje = "Se debe introducir un rfc";
            } else {
                mensaje = "RFC incompleto";
            }

            tilRfcCliente.setErrorEnabled(true);
            tilRfcCliente.setError(mensaje);
            return false;
        }
    }

    private boolean razonValido() {
        String razon = etNombreRazon.getText().toString();
        if (!razon.equals("")) {
            tilNombreRazon.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar Nombre o Razon Social";
            tilNombreRazon.setErrorEnabled(true);
            tilNombreRazon.setError(mensaje);
            return false;
        }
    }

    private boolean emailValido() {
        String cadena = etCorreo.getText().toString();
        if (isEmailValid(cadena)) {
            tilCorreo.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Email invalido";
            tilCorreo.setErrorEnabled(true);
            tilCorreo.setError(mensaje);
            return false;
        }
    }

    private boolean calleValido() {
        String cadena = etCalle.getText().toString();
        if (!cadena.equals("")) {
            tilCalle.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar Calle";
            tilCalle.setErrorEnabled(true);
            tilCalle.setError(mensaje);
            return false;
        }
    }

    private boolean numIntValido() {
        String cadena = etNumInt.getText().toString();
        if (!cadena.equals("")) {
            tilNumInt.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar numero interior";
            tilNumInt.setErrorEnabled(true);
            tilNumInt.setError(mensaje);
            return false;
        }
    }

    private boolean coloniaValido() {
        String cadena = etColonia.getText().toString();
        if (!cadena.equals("")) {
            tilColonia.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar numero interior";
            tilColonia.setErrorEnabled(true);
            tilColonia.setError(mensaje);
            return false;
        }
    }

    private boolean localidadValido() {
        String cadena = etLocalidad.getText().toString();
        if (!cadena.equals("")) {
            tilLocalidad.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar localidad";
            tilLocalidad.setErrorEnabled(true);
            tilLocalidad.setError(mensaje);
            return false;
        }
    }

    private boolean municipioValido() {
        String cadena = etMunicipio.getText().toString();
        if (!cadena.equals("")) {
            tilMunicipio.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar localidad";
            tilMunicipio.setErrorEnabled(true);
            tilMunicipio.setError(mensaje);
            return false;
        }
    }

    private boolean estadoValido() {
        String cadena = etEstado.getText().toString();
        if (!cadena.equals("")) {
            tilEstado.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar estado";
            tilEstado.setErrorEnabled(true);
            tilEstado.setError(mensaje);
            return false;
        }
    }

    private boolean paisValido() {
        String cadena = etPais.getText().toString();
        if (!cadena.equals("")) {
            tilPais.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar Pais";
            tilPais.setErrorEnabled(true);
            tilPais.setError(mensaje);
            return false;
        }
    }

    private boolean cpValido() {
        String cadena = etCp.getText().toString();
        if (!cadena.equals("")) {
            tilCp.setErrorEnabled(false);
            return true;
        } else {
            String mensaje = "Colocar codigo postal";
            tilCp.setErrorEnabled(true);
            tilCp.setError(mensaje);
            return false;
        }
    }

    private boolean formularioDomicilio() {
        return calleValido() && numIntValido() && coloniaValido() && localidadValido() && municipioValido()
                && estadoValido() && paisValido() && cpValido();
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    private boolean formularioCliente() {
        return rfcValido() && razonValido() && emailValido();
    }


    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        toolbar.setTitleTextColor(Color.parseColor(colorTexto));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrar Cliente");
    }


    public void registarCliente(View view) {
        //ArrayList<String> listaParams = new ArrayList<String>();
        hideSoftKeyboard();
        if (formularioCliente() && formularioDomicilio()) {
            //Crear peticion
            peticion(ServicioWeb.urlBase + ServicioWeb.REGISTRAR_CLIENTE);
        } else {
            //error
            Toast toast = Toast.makeText(getApplicationContext(), "Faltaron algunos campos.",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
            toast.show();
        }

    }

    private void peticion(String url, final String... par) {

        Log.d(TAG, "*******************    url     *****************************");
        Log.d(TAG, url);
        RequestQueue queue = Volley.newRequestQueue(context);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Registrando cliente...");
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

                                //regresar a la pagina
                                onBackPressed();
                                //Log.d(TAG, "" + response.toString());
                            } else {
                                //cargarDialog("");
                                Log.d(TAG, "*******************    Error     *****************************");
                                Log.d(TAG, "" + jsonResponse.getString("error"));
                            }

                        } catch (Exception e) {
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
                        Toast toast = Toast.makeText(getApplicationContext(), "Error en la peticion",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                        toast.show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postParam = new HashMap<String, String>();

                Map<String, String> paramsClie = new HashMap<>();
                Map<String, String> paramsDom = new HashMap<>();
                // the POST parameters:
                //params.put("rfc", par[0]);
                paramsClie.put("rfc", etRfcCliente.getText().toString());
                paramsClie.put("nombre", etNombreRazon.getText().toString());
                paramsClie.put("correo", etCorreo.getText().toString());
                paramsDom.put("calle", etCalle.getText().toString());
                paramsDom.put("numInt", etNumInt.getText().toString());
                paramsDom.put("numExt", etNumExt.getText().toString());
                paramsDom.put("colonia", etColonia.getText().toString());
                paramsDom.put("referencia", etReferencia.getText().toString());
                paramsDom.put("localidad", etLocalidad.getText().toString());
                paramsDom.put("municipio", etMunicipio.getText().toString());
                paramsDom.put("estado", etEstado.getText().toString());
                paramsDom.put("pais", etPais.getText().toString());
                paramsDom.put("cp", etCp.getText().toString());


                postParam.put("jsonCliente", new JSONObject(paramsClie).toString());
                postParam.put("jsonDomicilio", new JSONObject(paramsDom).toString());
                return postParam;
            }


        };

        queue.add(postRequest);


    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void cargarDialog(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }


    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(context, FacturarCompra.class);
        intent.putExtra(Extras.ID_COMPRA, compraEncontrada.getID());
        intent.putExtra(Extras.ID_FRANQUICIA, itemDetallado.getId());
        startActivity(intent);
    }
}
