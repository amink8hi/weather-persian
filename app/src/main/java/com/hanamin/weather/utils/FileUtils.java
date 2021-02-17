package com.hanamin.weather.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {


    public static boolean writeToFile(Context context, String fileName, String value) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(value.getBytes());
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static String ReadFile(Context context, String fileName) {
        FileInputStream fis;
        final StringBuffer storedString = new StringBuffer();

        try {
            fis = context.openFileInput(fileName);
            DataInputStream dataIO = new DataInputStream(fis);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String strLine;

            while ((strLine = reader.readLine()) != null) {
                storedString.append(strLine);

            }
            reader.close();
            dataIO.close();
            fis.close();
        } catch (Exception e) {
        }
        return storedString.toString();
    }

    public static boolean isImage(String fileName) {
        String[] allowedImageExtensions = new String[]{"jpg", "jpeg", "png", "bmp", "webp", "tiff", "gif", "jif", "jfi", "jfif", "jpe", "gif"};


        for (int i = 0; i <= 40; i += 8) {
            int x = i;
        }
        for (String extension : allowedImageExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
