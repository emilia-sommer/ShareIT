package com.example.shareit;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AngebotVerwalten extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Angebot> angebote = new ArrayList<Angebot>();
    public AdapterVerwaltung adapter;

    String angebot_loeschen_feedback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.angebot_verwalten);

        recyclerView = findViewById(R.id.recyclerViewXML);

        ImageButton buttonHome = (ImageButton) findViewById(R.id.imageButtonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ausgewählteKategorien = "1, 2, 3, 4, 5";
                Intent intent = new Intent(AngebotVerwalten.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);
            }
        });

        datenHerunterladen();

        adapter = new AdapterVerwaltung(this, angebote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void datenHerunterladen() {
        SharedPreferences sharedPreferences = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", "default");
        Log.d("MyActivity", "userID: " + userID);

        Log.d("MyActivity", userID);
        DownloadData task = new DownloadData();
        try {
            task.execute ("https://shareit.geschwister-scholl-gymnasium.de/datenAbrufenVerwaltung.php?userID=" + URLEncoder.encode(userID, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("MyActivity", "Task executed");

        try {
            InputStream stream = new ByteArrayInputStream(task.get().getBytes(StandardCharsets.UTF_8));
            InputStreamReader input = new InputStreamReader(stream, "UTF8");
            BufferedReader reader = new BufferedReader(input, 2000);
            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("§<>§°/");
                Angebot angebot = new Angebot(tokens[0], tokens[1], tokens[2] + " " + tokens[3] + ", " + tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]);
                angebote.add(angebot);
                Log.d("MyActivity", "Just created: " + angebot);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (angebote.isEmpty()){
            Intent intent = new Intent(AngebotVerwalten.this, FehlermeldungKeineEingestelltenAngebote.class);
            startActivity(intent);
            finish();
        }
    }
}