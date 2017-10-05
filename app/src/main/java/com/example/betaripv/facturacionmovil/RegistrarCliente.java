package com.example.betaripv.facturacionmovil;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.betaripv.facturacionmovil.clases.Franquicia;
import com.example.betaripv.facturacionmovil.utilerias.Colores;

import java.util.ArrayList;

public class RegistrarCliente extends AppCompatActivity {

    private EditText etRfcCliente, etNombreRazon, etCorreo,  etCalle,  etNumInt, etNumExt,
        etColonia, etLocalidad, etReferencia, etMunicipio, etEstado, etPais, etCp;

    private TextInputLayout tilRfcCliente, tilNombreRazon, tilCorreo,  tilCalle,  tilNumInt, tilNumExt,
            tilColonia, tilLocalidad, tilReferencia, tilMunicipio, tilEstado, tilPais, tilCp;

    private Button btnRegistar;
    private Franquicia itemDetallado;
    private View view;
    private String colorTexto, colorFondo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cliente);
        itemDetallado = Franquicia.getItem(getIntent().getIntExtra(ActividadDetalle.ID_FRANQUICIA, 0));

        etRfcCliente = (EditText) findViewById(R.id.textRFC);
        tilRfcCliente = (TextInputLayout) findViewById(R.id.layout_textRFC);

        etNombreRazon = (EditText) findViewById(R.id.textNombre);
        tilNombreRazon = (TextInputLayout) findViewById(R.id.layout_textNombre);

        etCorreo = (EditText) findViewById(R.id.textCorreo);
        tilCorreo = (TextInputLayout) findViewById(R.id.layout_textCorreo);

        etCalle = (EditText) findViewById(R.id.textCalle);
        tilCorreo = (TextInputLayout) findViewById(R.id.layout_textCalle);

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

        colorTexto = Colores.textColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        colorFondo = Colores.backgroundColor(Color.parseColor(itemDetallado.getColorSecundario()));
        view.setBackgroundColor(Color.parseColor(colorFondo));
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(itemDetallado.getColorPrincipalDark());
        }

        Colores.setColoresLayout(tilRfcCliente,itemDetallado,colorFondo);
        Colores.setColoresLayout(tilNombreRazon,itemDetallado,colorFondo);


        Colores.setColoresEdit(etRfcCliente,itemDetallado,colorFondo);
        Colores.setColoresEdit(etNombreRazon,itemDetallado,colorFondo);
        Colores.setColoresEdit(etCorreo,itemDetallado,colorFondo);
        Colores.setColoresEdit(etCalle,itemDetallado,colorFondo);
        Colores.setColoresEdit(etNumInt,itemDetallado,colorFondo);
        Colores.setColoresEdit(etNumExt,itemDetallado,colorFondo);
        Colores.setColoresEdit(etColonia,itemDetallado,colorFondo);
        Colores.setColoresEdit(etLocalidad,itemDetallado,colorFondo);
        Colores.setColoresEdit(etReferencia,itemDetallado,colorFondo);
        Colores.setColoresEdit(etMunicipio,itemDetallado,colorFondo);
        Colores.setColoresEdit(etEstado,itemDetallado,colorFondo);
        Colores.setColoresEdit(etPais,itemDetallado,colorFondo);
        Colores.setColoresEdit(etCp,itemDetallado,colorFondo);

        Colores.setColoresBtn(btnRegistar, itemDetallado);




        usarToolbar();


    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        toolbar.setTitleTextColor(Color.parseColor(colorTexto));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrar Cliente");


    }


    private void registrar(View view){
        ArrayList<String> listaParams = new ArrayList<String>();
        if(formularioValido()){

        }

    }

    private boolean formularioValido(){
        return true;
    }

    private void peticion(String url, ArrayList<String> listaParam) {

    }


}
