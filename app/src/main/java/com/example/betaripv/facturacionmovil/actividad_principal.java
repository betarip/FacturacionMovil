package com.example.betaripv.facturacionmovil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.betaripv.facturacionmovil.clases.Franquicia;
import com.example.betaripv.facturacionmovil.utilerias.Extras;

public class actividad_principal extends ActividadBase implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private AdapatadorDeFranquicias adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        usarToolbar();
        gridView = (GridView) findViewById(R.id.grid);
        adaptador = new AdapatadorDeFranquicias(this);
        gridView.setAdapter(adaptador);
        gridView.setOnItemClickListener(this);

    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }






    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Franquicia item = (Franquicia) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, ActividadDetalle.class);
        intent.putExtra(Extras.ID_FRANQUICIA, item.getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.imagen_franquicia),
                                    Extras.VIEW_NAME_HEADER_IMAGE)
                    );
            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());

        } else {
            startActivity(intent);
        }
    }


}
