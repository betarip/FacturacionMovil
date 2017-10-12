package com.example.betaripv.facturacionmovil.clases;

import android.util.Log;

import com.example.betaripv.facturacionmovil.FacturarCompra;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by betaripv on 13/09/17.
 */

public class Cliente {

    public static final String TAG = FacturarCompra.class.getSimpleName();

    private String idCliente;
    private String rfc;
    private String razonNombre;
    private String correo;
    private Domicilio domicilio;

    public static Cliente clienteSelec;

    public Cliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonNombre() {
        return razonNombre;
    }

    public void setRazonNombre(String razonNombre) {
        this.razonNombre = razonNombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public static Cliente getClienteSelec() {
        return clienteSelec;
    }

    public static void cleanClienteSelec() {
        clienteSelec = null;
    }

    public static boolean isSelected(){
        return clienteSelec != null;
    }

    public static Cliente JsontToCliente(JSONObject jsonCliente){
        Cliente n = null;
        try {

            //JSONObject jsonCliente = json.getJSONObject("datos");

            n = new Cliente(jsonCliente.getString("id_cliente"));
            n.setRfc(jsonCliente.getString("rfc"));
            n.setRazonNombre(jsonCliente.getString("razon_nombre"));
            n.setCorreo(jsonCliente.getString("correo"));
            JSONObject jsonDomicilio = jsonCliente.getJSONObject("dom_fiscal");

            Domicilio d = new Domicilio(jsonDomicilio.getString("id_dom_fiscal"));
            d.setCalle(jsonDomicilio.getString("calle"));
            d.setExt(jsonDomicilio.getString("num_ext"));
            d.setInte(jsonDomicilio.getString("num_int"));
            d.setColonia(jsonDomicilio.getString("colonia"));
            d.setLocalidad(jsonDomicilio.getString("localidad"));
            d.setRef(jsonDomicilio.getString("referencia"));
            d.setMunicipio(jsonDomicilio.getString("municipio"));
            d.setEstado(jsonDomicilio.getString("estado"));
            d.setPais(jsonDomicilio.getString("pais"));
            d.setCp(jsonDomicilio.getString("cp"));

            n.setDomicilio(d);
            clienteSelec = n;
            
        } catch (JSONException e) {
            Log.d(TAG, jsonCliente.toString());
            e.printStackTrace();

        }finally {
            return n;
        }
    }
}
