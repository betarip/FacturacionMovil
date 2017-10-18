package com.example.betaripv.facturacionmovil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
        ActivityCompat.requestPermissions(VistaFactura.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);


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
        //readPermission();
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
        Intent home = new Intent(this, actividad_principal.class);

        home.addCategory(Intent.CATEGORY_HOME);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }

    public void readPermission(){

        Log.d(TAG, "Lectura de permisos");
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {

                for (String p : info.requestedPermissions) {
                    Log.d(TAG, "Permission : " + p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(context, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
