package com.example.betaripv.facturacionmovil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.betaripv.facturacionmovil.clases.*;
import com.example.betaripv.facturacionmovil.utilerias.Archivos;
import com.example.betaripv.facturacionmovil.utilerias.Colores;
import com.example.betaripv.facturacionmovil.utilerias.Extras;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VistaFactura extends ActividadBase {
    public static final String TAG = VistaFactura.class.getSimpleName();

    ProgressDialog pDialog;

    final Context context = this;

    private Factura facturaGenerada;
    private Franquicia itemDetallado;
    private View view;
    private Button botonPDF, botonXML, botonMain;
    private String colorTexto, colorFondo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_factura);
        view = findViewById(R.id.principal);

        botonPDF = (Button) findViewById(R.id.btnPDF);
        botonXML = (Button) findViewById(R.id.btnXML);
        botonMain = (Button) findViewById(R.id.regresarMain);
        facturaGenerada = Factura.getFacturaSelect();
        itemDetallado = Franquicia.getItem(getIntent().getIntExtra(Extras.ID_FRANQUICIA, 0));

        colorTexto = Colores.textColor(Color.parseColor(itemDetallado.getColorPrincipal()));

        Colores.setColoresBtn(botonXML, itemDetallado);
        Colores.setColoresBtn(botonPDF, itemDetallado);
        Colores.setColoresBtn(botonMain, itemDetallado);

        colorFondo = Colores.backgroundColor(Color.parseColor(itemDetallado.getColorSecundario()));
        view.setBackgroundColor(Color.parseColor(colorFondo));
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = this.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(itemDetallado.getColorPrincipalDark());
        }

        usarToolbar();


    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        toolbar.setTitleTextColor(Color.parseColor(colorTexto));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Proceso final factura");


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    public void guardarPDF(View view) {
        /*Log.d(TAG, "*******************    PDF     *****************************");
        Log.d(TAG, facturaGenerada.getPdf().toString());
        */

        Date hoy = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        String fecha = format.format(hoy);

        fecha = fecha + " " + itemDetallado.getRfc();

        Archivos.base64ToPdf(facturaGenerada.getPdf(), fecha);


    }

    public void guardarXML(View view) {
        /*
        Log.d(TAG, "*******************    XML     *****************************");
        Log.d(TAG, facturaGenerada.getXml().toString());
        */
    }

    public void toMain(View view) {

    }
}
