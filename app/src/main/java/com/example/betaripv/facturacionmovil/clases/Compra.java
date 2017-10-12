package com.example.betaripv.facturacionmovil.clases;


/**
 * Created by ivan on 04/09/2017.
 */

public class Compra {
    private String idCompra;
    private String total;
    private String subtotal;

    public static Compra compraSelect;

    public Compra(String idCompra) {
        this.setIdCompra(idCompra);
    }


    public int getID() {
        return getIdCompra().hashCode();
    }

    public static Compra getCompraSelect (){
        return compraSelect;

    }

    public static void setCompraSelect(Compra compraSelect){
        Compra.compraSelect = compraSelect;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


}