package com.example.betaripv.facturacionmovil;

/**
 * Created by betaripv on 13/09/17.
 */

public class Cliente {

    private String idCliente;
    private String rfc;
    private String razonNombre;
    private String corre;
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

    public String getCorre() {
        return corre;
    }

    public void setCorre(String corre) {
        this.corre = corre;
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

    public static void setClienteSelec(Cliente clienteSelec) {
        Cliente.clienteSelec = clienteSelec;
    }

    public static boolean isSelected(){
        return clienteSelec != null;
    }

    public static void JsontToCliente(String json){

    }
}
