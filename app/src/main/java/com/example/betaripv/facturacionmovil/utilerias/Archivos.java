package com.example.betaripv.facturacionmovil.utilerias;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by betaripv on 12/10/17.
 */

public class Archivos {
    public static final String TAG = Archivos.class.getSimpleName();


    public static Uri base64ToPdfExternal(String baseString, String fileName) {
        Uri u = null;
        File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".pdf");
        byte[] pdfAsBytes = Base64.decode(baseString, 0);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(dwldsPath, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
            u = Uri.fromFile(dwldsPath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return u;

    }

    public static Uri XMLExternal(String xml, String fileName) {
        Uri u = null;
        File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".xml");
        byte[] pdfAsBytes = xml.getBytes();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(dwldsPath, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
            u = Uri.fromFile(dwldsPath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return u;

    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
