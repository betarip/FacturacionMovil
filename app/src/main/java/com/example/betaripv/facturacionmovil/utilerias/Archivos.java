package com.example.betaripv.facturacionmovil.utilerias;

import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by betaripv on 12/10/17.
 */

public class Archivos {

    public static FileOutputStream base64ToPdf(String baseString, String fileName) {
        final File dwldsPath = new File(DOWNLOADS_FOLDER + fileName + ".pdf");
        byte[] pdfAsBytes = Base64.decode(baseString, 0);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(dwldsPath, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;


    }
}
