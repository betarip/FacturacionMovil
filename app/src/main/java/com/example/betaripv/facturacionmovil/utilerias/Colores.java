package com.example.betaripv.facturacionmovil.utilerias;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.betaripv.facturacionmovil.R;
import com.example.betaripv.facturacionmovil.clases.Franquicia;

import java.lang.reflect.Field;

/**
 * Created by ivan on 05/09/2017.
 */

public class Colores {

    static int[][] states = new int[][] {
            new int[] { android.R.attr.state_enabled}, // enabled
            new int[] {-android.R.attr.state_enabled}, // disabled
            new int[] {-android.R.attr.state_checked}, // unchecked
            new int[] { android.R.attr.state_pressed},  // pressed

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

    public static void setColoresEdit(EditText edt, Franquicia f, final String fondo) {

        //edt.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
        int colorFondo = Color.parseColor(fondo);
        edt.setTextColor(Color.parseColor(textColor(colorFondo)));
        edt.setHintTextColor(Color.GREEN);
        //edt.set
        //edt.setHintTextColor();

        int[] colors = new int[] {
                f.getColorSecundarioLight(),
                Color.YELLOW,
                f.getColorSecundarioLight(),
                Color.parseColor(f.getColorSecundario()),
        };

        int[] colorsP = new int[] {
                f.getColorPrincipalLight(),
                Color.RED,
                f.getColorPrincipalLight(),
                Color.BLUE
        };
        /*
        Drawable drawable = edt.getBackground(); // get current EditText drawable
        //edt.setBackgroundTintList();
        drawable.setColorFilter(Color.parseColor(f.getColorPrincipal()), PorterDuff.Mode.SRC_ATOP);
        if(Build.VERSION.SDK_INT > 16) {
            edt.setBackground(drawable); // set the new drawable to EditText
            //edt.setB
        }else{
            edt.setBackgroundDrawable(drawable); // use setBackgroundDrawable because setBackground required API 16
        }
*/
        if (Build.VERSION.SDK_INT >= 21) {
            edt.setBackgroundTintList(new ColorStateList(states, colors));
            //edt.setHintTextColor(new ColorStateList(states, colorsP));
            //edt.setForegroundTintList(new ColorStateList(states, colors));
            //edt.setTi
            //edt.setHintTextColor(new ColorStateList(states, colors));
        }else {
            edt.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
        }



    }

    public static void setColoresLayout(TextInputLayout til, Franquicia f, String fondo) {

        //edt.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
        int colorFondo = Color.parseColor(fondo);

        //til.setHintTextAppearance();
        //til.setTextColor(Color.parseColor(textColor(colorFondo)));
        til.setHintTextAppearance(R.color.textLight);
        //til.setBackgroundColor(Color.BLUE);
        //til.setHintTextColor(Color.parseColor(textColor(colorFondo)));
        //til.setT e
        /*
        int[] colors = new int[] {
                f.getColorSecundarioLight(),
                Color.RED,
                f.getColorSecundarioLight(),
                Color.parseColor(f.getColorSecundario()),
        };
        if (Build.VERSION.SDK_INT >= 21) {
            til.setBackgroundTintList(new ColorStateList(states, colors));

        }else {
            til.setBackgroundColor(Color.parseColor(f.getColorSecundario()));
        }
*/
    }


    //public static void configurarBoton()


    public static void setInputTextLayoutColor(EditText editText, @ColorInt int color) {
        TextInputLayout til = (TextInputLayout) editText.getParent();
        try {
            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{ color }));

            Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            fFocusedTextColor.setAccessible(true);
            fFocusedTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{ color }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
