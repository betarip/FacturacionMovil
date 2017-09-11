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

    @NonNull
    public static String textColor(int color) {
        if (Color.red(color) * 0.299 +
                Color.green(color) * 0.587 +
                Color.blue(color) * 0.144 > 186
                ) return "#000000";
        else
            return "#FFFFFF";
    }

    public static void setColoresBtn(Button btn, Franquicia f) {
        btn.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
        btn.setTextColor(Color.parseColor(textColor(Color.parseColor(f.getColorSecundario()))));
    }

    public static void setColoresEdit(EditText edt, Franquicia f, int fondo) {

        //edt.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
        edt.setTextColor(Color.parseColor(textColor(fondo)));
        edt.setHintTextColor(Color.parseColor(textColor(fondo)));
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };
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
