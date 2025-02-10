package com.example.shareit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class AngebotEinstellen extends AppCompatActivity {

    String neues_angebot_titel;
    String neues_angebot_beschreibung;
    String neues_angebot_kategorie;
    String angebot_einstellen_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.angebot_einstellen);

        EditText editText_titel = (EditText) findViewById(R.id.neues_angebot_titel);
        EditText editText_beschreibung = (EditText) findViewById(R.id.neues_angebot_beschreibung);
        Spinner spinner_kategorie = (Spinner) findViewById(R.id.neues_angebot_kategorie);

        ImageButton buttonHome = (ImageButton) findViewById(R.id.imageButtonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ausgewählteKategorien = "1, 2, 3, 4, 5";
                Intent intent = new Intent(AngebotEinstellen.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);
            }
        });

        Button buttonAngebotEinstellen = (Button) findViewById(R.id.button_angebot_einstellen);
        buttonAngebotEinstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                neues_angebot_titel = editText_titel.getText().toString();
                neues_angebot_beschreibung = editText_beschreibung.getText().toString();

                if (neues_angebot_titel.isEmpty() || neues_angebot_beschreibung.isEmpty()) {
                    Toast toast_true = Toast.makeText(AngebotEinstellen.this, "Du musst einen Titel und eine Beschreibung eingeben.", Toast.LENGTH_LONG);
                    toast_true.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast_true.show();

                } else {
                    neues_angebot_kategorie = spinner_kategorie.getSelectedItem().toString();

                    if(neues_angebot_kategorie.equals("Bücher")) {neues_angebot_kategorie = "1";}
                    if(neues_angebot_kategorie.equals("Zeitschriften")) {neues_angebot_kategorie = "2";}
                    if(neues_angebot_kategorie.equals("CDs")) {neues_angebot_kategorie = "3";}
                    if(neues_angebot_kategorie.equals("DVDs")) {neues_angebot_kategorie = "4";}
                    if(neues_angebot_kategorie.equals("Sonstiges")) {neues_angebot_kategorie = "5";}

                    Log.d("MyActivity", "Variable neues_angebot_titel: " + neues_angebot_titel);
                    Log.d("MyActivity", "Variable neues_angebot_beschreibung: " + neues_angebot_beschreibung);
                    Log.d("MyActivity", "Variable neues_angebot_kategorie: " + neues_angebot_kategorie);

                    SharedPreferences sharedPreferences = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
                    String neues_angebot_benutzer = sharedPreferences.getString("userID", "default");
                    Log.d("MyActivity", "neues_angebot_benutzer: " + neues_angebot_benutzer);

                    Intent intent = new Intent(AngebotEinstellen.this, AngebotEinstellenWartemeldung.class);
                    intent.putExtra("neues_angebot_titel", neues_angebot_titel);
                    intent.putExtra("neues_angebot_beschreibung", neues_angebot_beschreibung);
                    intent.putExtra("neues_angebot_kategorie", neues_angebot_kategorie);
                    intent.putExtra("neues_angebot_benutzer", neues_angebot_benutzer);
                    startActivity(intent);
                }
            }
        });
    }
}