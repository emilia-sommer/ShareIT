package com.example.shareit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

public class AngebotEinstellenWartemeldung extends AppCompatActivity {

    String angebot_einstellen_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.angebot_einstellen_wartemeldung);
        angebotEinstellen();
    }

    public void angebotEinstellen() {
        Log.d("MyActivity", "angebotEinstellen() wurde gestartet");

        String neues_angebot_titel = getIntent().getStringExtra("neues_angebot_titel");
        String neues_angebot_beschreibung = getIntent().getStringExtra("neues_angebot_beschreibung");
        String neues_angebot_kategorie = getIntent().getStringExtra("neues_angebot_kategorie");
        String neues_angebot_benutzer = getIntent().getStringExtra("neues_angebot_benutzer");

        Log.d("MyActivity", "AngebotEinstellenWartemeldung.java, neues_angebot_titel: " + neues_angebot_titel);
        Log.d("MyActivity", "AngebotEinstellenWartemeldung.java, neues_angebot_beschreibung: " + neues_angebot_beschreibung);
        Log.d("MyActivity", "AngebotEinstellenWartemeldung.java, neues_angebot_kategorie: " + neues_angebot_kategorie);
        Log.d("MyActivity", "AngebotEinstellenWartemeldung.java, neues_angebot_benutzer: " + neues_angebot_benutzer);

        AngebotEinstellenPHP task = new AngebotEinstellenPHP();

        try {
            String url = "https://shareit.geschwister-scholl-gymnasium.de/angebotEinstellen.php?neues_angebot_titel=" + URLEncoder.encode(neues_angebot_titel, "UTF-8");
            url = url + "&neues_angebot_beschreibung=" + URLEncoder.encode(neues_angebot_beschreibung, "UTF-8");
            url = url + "&neues_angebot_kategorie=" + URLEncoder.encode(neues_angebot_kategorie, "UTF-8");
            url = url + "&neues_angebot_benutzer=" + URLEncoder.encode(neues_angebot_benutzer, "UTF-8");
            Log.d("MyActivity", "URL: " + url);
            task.execute(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("MyActivity", "Task executed");

        try {
            Log.d("MyActivity", "Checkpoint 1");
            InputStream stream = new ByteArrayInputStream(task.get().getBytes(StandardCharsets.UTF_8));
            Log.d("MyActivity", "Checkpoint 2");
            InputStreamReader input = new InputStreamReader(stream, "UTF8");
            Log.d("MyActivity", "Checkpoint 3");
            BufferedReader reader = new BufferedReader(input, 2000);
            Log.d("MyActivity", "Checkpoint 4");
            angebot_einstellen_feedback = reader.readLine();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("MyActivity", "angebot_einstellen_feedback: " + angebot_einstellen_feedback);

        if (angebot_einstellen_feedback.equals("true")) {
            Toast toast_true = Toast.makeText(this, "Dein Angebot wurde soeben eingestellt.", Toast.LENGTH_LONG);
            toast_true.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast_true.show();
        } else {
            if (angebot_einstellen_feedback.equals("keine Internetverbindung")) {
                Toast toast_internet = Toast.makeText(this, "Du hast keine Internetverbindung.", Toast.LENGTH_LONG);
                toast_internet.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast_internet.show();
            } else {
                Toast toast_false = Toast.makeText(this, "Dein Angebot konnte leider nicht eingestellt werden.", Toast.LENGTH_LONG);
                toast_false.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast_false.show();
            }
        }

        neues_angebot_titel = null;
        neues_angebot_beschreibung = null;
        neues_angebot_kategorie = null;

        String ausgewählteKategorien = "1, 2, 3, 4, 5";
        Intent intent = new Intent(AngebotEinstellenWartemeldung.this, MainActivity.class);
        intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
        startActivity(intent);
    }
}
