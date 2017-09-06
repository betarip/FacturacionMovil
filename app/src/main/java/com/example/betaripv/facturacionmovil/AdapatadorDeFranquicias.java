package com.example.betaripv.facturacionmovil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by betaripv on 13/08/17.
 * <p>
 * {@link android.widget.BaseAdapter} para poblar franquicias en un grid view
 */
public class AdapatadorDeFranquicias extends BaseAdapter {
    private Context context;
    private List<Franquicia> franquicias;

    public AdapatadorDeFranquicias(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Franquicia.listaFranquicias.size();
    }

    @Override
    public Franquicia getItem(int position) {
        return Franquicia.listaFranquicias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @TargetApi(Build.VERSION_CODES.M)

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        ImageView imagenFranquicia = (ImageView) convertView.findViewById(R.id.imagen_franquicia);
        TextView nombreFranquicia = (TextView) convertView.findViewById(R.id.nombre_franquicia);

        final Franquicia item = getItem(position);
        //imagenFranquicia.setImageResource(item.getIdDrawable());
        byte[] decodeImage = Base64.decode(item.getImagen().getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
        imagenFranquicia.setImageBitmap(decodedByte);


        nombreFranquicia.setText(item.getNombre());

        return convertView;
    }


}
