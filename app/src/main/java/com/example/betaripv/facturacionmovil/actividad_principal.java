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

public class actividad_principal extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}
