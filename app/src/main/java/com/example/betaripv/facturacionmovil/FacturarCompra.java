package com.example.betaripv.facturacionmovil;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FacturarCompra extends AppCompatActivity {
    public static final String TAG = FacturarCompra.class.getSimpleName();
    public static final String ID_COMPRA = "com.example.betaripv.facturacionmovil.compra.ID";
    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";

    ProgressDialog pDialog;

    final Context context = this;
    private Franquicia itemDetallado;
    private View view;
    private Button buscarCliente, mostrarCliente, mostrarCompra, facturar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facturar_compra);
        itemDetallado = Franquicia.getItem(getIntent().getIntExtra(ActividadDetalle.ID_FRANQUICIA, 0));
        view = findViewById(R.id.principal);
        usarToolbar();

    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor(itemDetallado.getColorPrincipal()));
        setSupportActionBar(toolbar);
        view.setBackgroundColor(Color.parseColor(itemDetallado.getColorSecundario()));

    }
}
