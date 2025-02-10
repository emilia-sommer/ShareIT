package com.example.shareit;
import android.content.Intent;
import android.content.SharedPreferences;
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
public class AngebotVerwaltenWartemeldung extends AppCompatActivity {

    String angebot_loeschen_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.angebot_verwalten_wartemeldung);
        try {
            angebotLoeschen();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void angebotLoeschen() throws ExecutionException, InterruptedException {
        Log.d("MyActivity", "angebotLoeschen() wurde gestartet");

        String titel_betroffenes_angebot = getIntent().getStringExtra("titel_betroffenes_angebot");
        String beschreibung_betroffenes_angebot = getIntent().getStringExtra("beschreibung_betroffenes_angebot");

        SharedPreferences sharedPreferences = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
        String benutzer_betroffenes_angebot = sharedPreferences.getString("userID", "default");

        Log.d("MyActivity", "AngebotVerwaltenWartemeldung.java, titel_betroffenes_angebot: " + titel_betroffenes_angebot);
        Log.d("MyActivity", "AngebotVerwaltenWartemeldung.java, beschreibung_betroffenes_angebot: " + beschreibung_betroffenes_angebot);
        Log.d("MyActivity", "AngebotVerwaltenWartemeldung.java, benutzer_betroffenes_angebot: " + benutzer_betroffenes_angebot);

        AngebotVerwaltenPHP task = new AngebotVerwaltenPHP();

        try{
            String url = "https://shareit.geschwister-scholl-gymnasium.de/angebotLoeschen.php?titel_betroffenes_angebot=" + URLEncoder.encode(titel_betroffenes_angebot, "UTF-8");
            url = url + "&beschreibung_betroffenes_angebot=" + URLEncoder.encode(beschreibung_betroffenes_angebot, "UTF-8");
            url = url + "&benutzer_betroffenes_angebot=" + URLEncoder.encode(benutzer_betroffenes_angebot, "UTF-8");
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
            angebot_loeschen_feedback = reader.readLine();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("MyActivity", "angebot_loeschen_feedback: " + angebot_loeschen_feedback);

        if (angebot_loeschen_feedback.equals("true")) {
            Toast toast_true = Toast.makeText(this, "Dein Angebot wurde soeben gelöscht.", Toast.LENGTH_LONG);
            toast_true.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast_true.show();
        } else {
            if (angebot_loeschen_feedback.equals("keine Internetverbindung")) {
                Toast toast_internet = Toast.makeText(this, "Du hast keine Internetverbindung.", Toast.LENGTH_LONG);
                toast_internet.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast_internet.show();
            } else {
                Toast toast_false = Toast.makeText(this, "Dein Angebot konnte leider nicht gelöscht werden.", Toast.LENGTH_LONG);
                toast_false.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast_false.show();
            }
        }

        Intent intent = new Intent(AngebotVerwaltenWartemeldung.this, AngebotVerwalten.class);
        startActivity(intent);
    }
}