package com.example.betaripv.facturacionmovil;

import android.graphics.Color;

import com.example.betaripv.facturacionmovil.utilerias.Colores;

import java.util.ArrayList;

/**
 * Created by betaripv on 13/08/17.
 */
public class Franquicia {

    private String idFranquicia;
    private String rfc;
    private String nombre;
    private String colorPrincipal;
    private int colorPrincipalDark;
    private int colorPrincipalLight;
    private String colorSecundario;
    private int colorSecundarioDark;
    private int colorSecundarioLight;
    private String imagen;

    private int idDrawable;

    public static ArrayList<Franquicia> listaFranquicias = new ArrayList<Franquicia>();


    Franquicia(String idFranquicia) {
        this.setIdFranquicia(idFranquicia);

    }

    public int getId() {
        return getIdFranquicia().hashCode();
    }


    public static Franquicia getItem(int id) {
        for (Franquicia fra : listaFranquicias) {
            if (fra.getId() == id) {
                return fra;
            }

        }
        return null;
    }

    public String getIdFranquicia() {
        return idFranquicia;
    }

    public void setIdFranquicia(String idFranquicia) {
        this.idFranquicia = idFranquicia;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public String getColorPrincipal() {
        return colorPrincipal;
    }

    public void setColorPrincipal(String colorPrincipal) {
        this.colorPrincipal = colorPrincipal;
        this.setColorPrincipalLight(Colores.manipuleColor(Color.parseColor(colorPrincipal), 1.2f));
        this.setColorPrincipalDark(Colores.manipuleColor(Color.parseColor(colorPrincipal), 0.8f));
        //this.colorPrincipalLight = Colores.lighter(Color.parseColor(colorPrincipal));
    }

    public String getColorSecundario() {
        return colorSecundario;
    }

    public void setColorSecundario(String colorSecundario) {

        this.colorSecundario = colorSecundario;
        this.setColorSecundarioDark(Colores.manipuleColor(Color.parseColor(colorSecundario), 0.8f));
        this.setColorSecundarioLight(Colores.manipuleColor(Color.parseColor(colorSecundario), 1.2f));
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public int getColorPrincipalDark() {
        return colorPrincipalDark;
    }

    public void setColorPrincipalDark(int colorPrincipalDark) {
        this.colorPrincipalDark = colorPrincipalDark;
    }

    public int getColorPrincipalLight() {
        return colorPrincipalLight;
    }

    public void setColorPrincipalLight(int colorPrincipalLight) {
        this.colorPrincipalLight = colorPrincipalLight;
    }

    public int getColorSecundarioDark() {
        return colorSecundarioDark;
    }

    public void setColorSecundarioDark(int colorSecundarioDark) {
        this.colorSecundarioDark = colorSecundarioDark;
    }

    public int getColorSecundarioLight() {
        return colorSecundarioLight;
    }

    public void setColorSecundarioLight(int colorSecundarioLight) {
        this.colorSecundarioLight = colorSecundarioLight;
    }
}
