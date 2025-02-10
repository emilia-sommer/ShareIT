package com.example.shareit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    String login_succeeded;
    String loginValuesIdentical;
    String benutzername;
    String passwort;
    String benutzer_id;
    String vorname;
    String nachname;
    String klasse;
    private android.app.Activity Activity;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_progressbar);

        loadingSpinner = findViewById(R.id.loadingSpinner);
        showSpinner();

        //SharedPreferences settings = this.getSharedPreferences("bereitsLogin", Context.MODE_PRIVATE);
        //settings.edit().clear().commit();

        checkIfAlreadyLoggedIn();
    }

    public void showSpinner() {
        loadingSpinner.setVisibility(View.VISIBLE);
        Log.d("MyActivity", "showSpinner aufgerufen");
    }

    public void hideSpinner() {
        loadingSpinner.setVisibility(View.GONE);
    }

    public void checkIfAlreadyLoggedIn(){

        SharedPreferences sharedPreferences = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
        String userSharedPref = sharedPreferences.getString("user", "");
        String loginStringSharedPref = sharedPreferences.getString("loginString", "");
        String completeSharedPref = userSharedPref + loginStringSharedPref;
        Log.d("MyActivity", "SharedPreferences (userSharedPref): " + userSharedPref);
        Log.d("MyActivity", "SharedPreferences (loginStringSharedPref): " + loginStringSharedPref);
        Log.d("MyActivity", "SharedPreferences (completeSharedPref): " + completeSharedPref);

        if(!completeSharedPref.equals("")){
            Log.d("MyActivity", "SharedPreferences != null");

            // hier: SharedPreferences == Datenbank Spalte "login"?
            LoginValuesIdenticalResult task = new LoginValuesIdenticalResult();
            try {
                String url = "https://shareit.geschwister-scholl-gymnasium.de/sharedPrefUeberpruefen.php?benutzername=" + URLEncoder.encode(userSharedPref, "UTF-8");
                url = url + "&sharedPreferences=" + URLEncoder.encode(loginStringSharedPref, "UTF-8");
                Log.d("MyActivity", "URL: " + url);
                task.execute(url);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }
            try {
                InputStream stream = new ByteArrayInputStream(task.get().getBytes(StandardCharsets.UTF_8));
                InputStreamReader input = new InputStreamReader(stream, "UTF8");
                BufferedReader reader = new BufferedReader(input, 2000);
                loginValuesIdentical = reader.readLine();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("MyActivity", "loginValuesIdentical: " + loginValuesIdentical);

            if(loginValuesIdentical.equals("true")){
                String ausgewählteKategorien = "1, 2, 3, 4, 5";
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);
            }

            if(loginValuesIdentical.equals("false")){
                SharedPreferences settings = this.getSharedPreferences("bereitsLogin", Context.MODE_PRIVATE);
                settings.edit().clear().commit();

                SharedPreferences sharedPreferences2 = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
                String userSharedPref2 = sharedPreferences.getString("user", "");
                String loginStringSharedPref2 = sharedPreferences.getString("loginString", "");
                String completeSharedPref2 = userSharedPref + loginStringSharedPref;
                Log.d("MyActivity", "SharedPreferences (completeSharedPref): " + completeSharedPref2);

                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
            }

            if(loginValuesIdentical.equals("keine Internetverbindung")){
                Toast toast = Toast.makeText(this, "Du hast keine Internetverbindung.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
            }

        }else {
            Log.d("MyActivity", "SharedPreferences == null");

            setContentView(R.layout.login);

            EditText editText_bn = (EditText) findViewById(R.id.login_username);
            EditText editText_pw = (EditText) findViewById(R.id.login_password);

            Button buttonLogin = (Button) findViewById(R.id.button_einloggen);
            buttonLogin.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    benutzername = editText_bn.getText().toString();

                    passwort = editText_pw.getText().toString();

                    checkLogin();
                }
            });
        }
    }

    public final Activity getActivity() {
        return Activity;
    }

    public void checkLogin(){
        LoginPHP task = new LoginPHP();

        try {
            Log.d("MyActivity", "Variable Benutzername: " + benutzername + " und Variable Passwort: " + passwort);
            String url = "https://shareit.geschwister-scholl-gymnasium.de/login.php?benutzername=" + URLEncoder.encode(benutzername, "UTF-8");
            url = url + "&passwort=" + URLEncoder.encode(passwort, "UTF-8");
            Log.d("MyActivity", "URL: " + url);
            task.execute(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            Log.d("MyActivity", "Checkpoint 1");
            InputStream stream = new ByteArrayInputStream(task.get().getBytes(StandardCharsets.UTF_8));
            Log.d("MyActivity", "Checkpoint 2");
            InputStreamReader input = new InputStreamReader(stream, "UTF8");
            Log.d("MyActivity", "Checkpoint 3");
            BufferedReader reader = new BufferedReader(input, 2000);
            Log.d("MyActivity", "Checkpoint 4");

            String output = reader.readLine();
            Log.d("MyActivity", "output: " + output);

            if(output.equals("false")) {
                login_succeeded = "false";
                Log.d("MyActivity", "login_succeeded: " + login_succeeded);
                resultLogin();
            } else {
                String[] tokens = output.split("TRENNUNG_DER_WERTE");
                login_succeeded = tokens[0];
                benutzer_id = tokens[1];
                vorname = tokens[2];
                nachname = tokens[3];
                klasse = tokens[4];

                Log.d("MyActivity", "login_succeeded: " + login_succeeded);
                Log.d("MyActivity", "benutzer_id: " + benutzer_id);
                resultLogin();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resultLogin() {
        if (login_succeeded.equals("false")) {
            Toast toast_false = Toast.makeText(this, "Benutzername und/oder Passwort ist falsch", Toast.LENGTH_LONG);
            toast_false.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast_false.show();
        } else {
            if (login_succeeded.equals("keine Internetverbindung")) {
                Toast toast = Toast.makeText(this, "Du hast keine Internetverbindung.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            } else {
                Toast toast_true = Toast.makeText(this, "Hallo " + vorname + "!", Toast.LENGTH_LONG);
                toast_true.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast_true.show();

                SharedPreferences.Editor editor = getSharedPreferences("bereitsLogin", MODE_PRIVATE).edit();
                editor.putString("user", benutzername);
                editor.putString("loginString", login_succeeded);
                editor.putString("userID", benutzer_id);
                editor.putString("vorname", vorname);
                editor.putString("nachname", nachname);
                editor.putString("klasse", klasse);
                editor.apply();

                SharedPreferences sharedPreferences = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
                String userSharedPref = sharedPreferences.getString("user", "default");
                String loginStringSharedPref = sharedPreferences.getString("loginString", "default");
                String useridSharedPref = sharedPreferences.getString("userID", "default");
                String vornameSharedPref = sharedPreferences.getString("vorname", "default");
                String nachnameSharedPref = sharedPreferences.getString("nachname", "default");
                String klasseSharedPref = sharedPreferences.getString("klasse", "default");
                String completeSharedPref = userSharedPref + loginStringSharedPref;
                Log.d("MyActivity", "SharedPreferences (userSharedPref): " + userSharedPref);
                Log.d("MyActivity", "SharedPreferences (loginStringSharedPref): " + loginStringSharedPref);
                Log.d("MyActivity", "SharedPreferences (useridSharedPref): " + useridSharedPref);
                Log.d("MyActivity", "SharedPreferences (completeSharedPref): " + completeSharedPref);
                Log.d("MyActivity", "SharedPreferences (vornameSharedPref): " + vornameSharedPref);
                Log.d("MyActivity", "SharedPreferences (nachnameSharedPref): " + nachnameSharedPref);
                Log.d("MyActivity", "SharedPreferences (klasseSharedPref): " + klasseSharedPref);

                String ausgewählteKategorien = "1,2,3,4,5";
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);

                Log.d("MyActivity", "login completed");

                hideSpinner();
            }
        }
    }
}