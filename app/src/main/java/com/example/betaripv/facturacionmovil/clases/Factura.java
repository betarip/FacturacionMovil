package com.example.betaripv.facturacionmovil.clases;

/**
 * Created by ivan on 12/10/2017.
 */

public class Factura {
    private String xml;
    private String pdf;

    public static Factura facturaSelect;

    public int getID() {
        return getXml().hashCode();
    }

    public static Factura getFacturaSelect(){
        return facturaSelect;
    }

    public static void setFacturaSelect(Factura facturaSelect){
        Factura.facturaSelect = facturaSelect;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public static boolean isSelected(){
        return facturaSelect != null;
    }

}
