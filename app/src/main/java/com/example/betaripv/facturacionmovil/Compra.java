package com.example.betaripv.facturacionmovil;

import java.util.ArrayList;

/**
 * Created by ivan on 04/09/2017.
 */

public class Compra {
    private String idCompra;
    private String total;
    private String subtotal;

    public static ArrayList<Compra> compras = new ArrayList<Compra>();

    Compra(String idCompra) {
        this.setIdCompra(idCompra);
    }


    public int getID() {
        return getIdCompra().hashCode();
    }

    public static Compra getItem(int id) {
        for (Compra c : compras) {
            if (c.getID() == id)
                return c;
        }
        return null;
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

    public static ArrayList<Compra> getCompras() {
        return compras;
    }

    public static void setCompras(ArrayList<Compra> compras) {
        Compra.compras = compras;
    }


}
