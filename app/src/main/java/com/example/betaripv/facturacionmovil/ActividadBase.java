package com.example.betaripv.facturacionmovil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betaripv.facturacionmovil.clases.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ivan on 11/10/2017.
 */

public class ActividadBase extends AppCompatActivity {

    // Activity code here

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ver) {
            SharedPreferences pref = getSharedPreferences("SPCliente", Context.MODE_PRIVATE);
            if (pref.contains("cliente")) {
                String json = pref.getString("cliente", "");
                try {
                    JSONObject jsonResponse = new JSONObject(json.toString());
                    Cliente nuevo = Cliente.JsontToCliente(jsonResponse);
                    mostrarDatosCliente(nuevo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                mostrarMensaje("No ha guardado el cliente en la aplicación");
            }
            return true;
        }

        if (id == R.id.eliminar) {
            SharedPreferences pref = getSharedPreferences("SPCliente", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            mostrarMensaje("Se eliminaron los datos del cliente en el telefono");
            editor.clear();
            editor.commit();
            return true;
        }
        if (id == R.id.registrar) {
            Intent intent;

            intent = new Intent(this, RegistrarCliente.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mostrarMensaje(String mensaje) {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje,
                Toast.LENGTH_SHORT);
        //toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
        toast.show();
    }

    public void mostrarDatosCliente(Cliente clienteEnc) {
        //colocarDatosClientes();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
}