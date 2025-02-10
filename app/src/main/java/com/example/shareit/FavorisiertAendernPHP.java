package com.example.shareit;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

public class FavorisiertAendernPHP extends AsyncTask<String, Void, String> {
    String favorisiertAendernResult = null;

    @Override
    protected String doInBackground(String... params) {

        try{
            favorisiertAendernResult = sendRequest(params[0]);
        } catch (Exception e) {
            return "keine Internetverbindung";
        }
        Log.d(this.getClass().getName(), favorisiertAendernResult);

        return favorisiertAendernResult;
    }

    public String sendRequest(String urlStr) throws Exception {

        if (urlStr == null || urlStr.isEmpty()) {
            throw new InvalidParameterException("Der Parameter urlStr ist null oder leer.");
        }

        //Hier wird die Verbindung hergestellt.
        HttpURLConnection conn = null;

        //Wenn es keine URL ist, wird eine Fehlermeldung ausgelöst.
        URL url = new URL (urlStr);
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);

        //Hier wird die Antwort erstellt.
        StringBuilder str = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";

        while ((line = reader.readLine()) !=null) {
            str.append(line + System.getProperty("line.separator"));
        }

        return str.toString();
    }
}