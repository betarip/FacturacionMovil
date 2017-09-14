package com.example.betaripv.facturacionmovil.utilerias;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

import com.example.betaripv.facturacionmovil.Franquicia;

/**
 * Created by ivan on 05/09/2017.
 */

public class Colores {

    static int[][] states = new int[][] {
            new int[] { android.R.attr.state_enabled}, // enabled
            new int[] {-android.R.attr.state_enabled}, // disabled
            new int[] {-android.R.attr.state_checked}, // unchecked
            new int[] { android.R.attr.state_pressed}  // pressed
    };

    public static int hexToIInt(String colorStr) {
        int color = Color.parseColor(colorStr);
        return color;
    }

   

    public static int manipuleColor(int color, float factor) {

        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255)
        );
    }

    public static int backManipuleColor(int color, float factor) {

        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255)
        );
    }

    public static String backgroundColor(int color){
        if (Color.red(color) * 0.299 +
                Color.green(color) * 0.587 +
                Color.blue(color) * 0.144 > 186
                ) return "#212121";
        else
            return "#f5f5f5";
    }

    public static String textColor(int color) {
        if (Color.red(color) * 0.299 +
                Color.green(color) * 0.587 +
                Color.blue(color) * 0.144 > 186
                ) return "#010101";
        else
            return "#FFFFFF";
    }



    public static void setColoresBtn(Button btn, Franquicia f) {
        int[] colorsBtn = new int[] {
                Color.parseColor(f.getColorSecundario()),
                f.getColorSecundarioLight(),
                Color.parseColor(f.getColorSecundario()),
                f.getColorSecundarioDark(),
        };
        String colorTexto = textColor(Color.parseColor(f.getColorSecundario()));
        float factor;
        if(colorTexto.equals("#010101")){
            factor = 100F;

        }else{
            factor = 0.3F;
        }

        int[] colorsTxt = new int[] {
                Color.parseColor(colorTexto),
                manipuleColor(Color.parseColor(colorTexto), factor),
                Color.parseColor(textColor(Color.parseColor(f.getColorSecundario()))),
                Color.parseColor(textColor(Color.parseColor(f.getColorSecundario()))),
        };
        if (Build.VERSION.SDK_INT >= 21) {
            btn.setBackgroundTintList(new ColorStateList(states, colorsBtn));
            btn.setTextColor(new ColorStateList(states, colorsTxt));
        }else{
            btn.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
            btn.setTextColor(Color.parseColor(textColor(Color.parseColor(f.getColorSecundario()))));
        }

    }

    public static void setColoresEdit(EditText edt, Franquicia f, String fondo) {

        //edt.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
        int colorFondo = Color.parseColor(fondo);
        edt.setTextColor(Color.parseColor(textColor(colorFondo)));
        edt.setHintTextColor(Color.parseColor(textColor(colorFondo)));

        int[] colors = new int[] {
                f.getColorSecundarioLight(),
                Color.RED,
                f.getColorSecundarioLight(),
                Color.parseColor(f.getColorSecundario()),
        };
        if (Build.VERSION.SDK_INT >= 21) {
            edt.setBackgroundTintList(new ColorStateList(states, colors));
        }
    }


    //public static void configurarBoton()
}
