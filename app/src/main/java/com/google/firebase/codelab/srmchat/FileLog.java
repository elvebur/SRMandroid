package com.google.firebase.codelab.srmchat;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

//import com.example.willian.noise2.Servicios.StorageHelper;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FileLog {

    BufferedWriter bufLog = null;
    BufferedWriter bufLog2 = null;
    private File logFile;
    private File logFile2;
    private String mLogFileName;
    private Context mContextClas;


    public FileLog(String SAMPLE_RATE, Context mContext) {
        Locale usLocale = new Locale("en", "US");
        Locale.setDefault(usLocale);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("ddMMMyyyy");
        mLogFileName = df.format(c.getTime()) + "_"+SAMPLE_RATE;
        mContextClas = mContext;

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        if (Environment.getExternalStorageState() == null) {
            return false;
        }

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void CreateFileLog(){

        if (isExternalStorageReadable() == false) {//por interno directorio

            logFile = new File(mContextClas.getFilesDir(), mLogFileName+".csv");
        }
        else
        {

            logFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), mLogFileName+".csv");
        }

        String test = logFile.toString();
        Log.d("Noise:", "Ruta" + test);
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }
        }

    }

    public void writeAudioDataToFile(String datos) {
        String sDataString ="";

        if (datos != "") {
            sDataString += datos;
            try {
                //BufferedWriter for performance, true to set append to file flag
                bufLog = new BufferedWriter(new FileWriter(logFile, true));
                bufLog.append(sDataString);
                bufLog.newLine();
                //close(buf);

                //buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                close(bufLog);
            }

        }
    }

    public void close(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (IOException e) {
            //log the exception
        }
    }
}
