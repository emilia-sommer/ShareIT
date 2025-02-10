package com.example.shareit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Angebot> angebote = new ArrayList<Angebot>();
    public AdapterMain adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewXML);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mainXML);
        setSupportActionBar(toolbar);

        datenHerunterladen();

        adapter = new AdapterMain(this, angebote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.updateXML:
                Log.d("MyActivity", "Aktualisierungsbutton");
                angebote.clear();
                datenHerunterladen();
                adapter.notifyDataSetChanged();
                Toast toast_update = Toast.makeText(this, "Die Angebote wurden aktualisiert.", Toast.LENGTH_LONG);
                toast_update.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast_update.show();
                return true;

            case R.id.filtersymbolXML:
                Log.d("MyActivity", "Filterbutton");
                Intent intent1 = new Intent(MainActivity.this, Filterfunktion.class);
                startActivity(intent1);
                return true;

            case R.id.angebotEinstellenXML:
                Log.d("MyActivity", "Einstellungsbutton");
                Intent intent2 = new Intent(MainActivity.this, AngebotEinstellen.class);
                startActivity(intent2);
                return true;

            case R.id.angeboteVerwaltenXML:
                Log.d("MyActivity", "Verwaltungsbutton");
                Intent intent3 = new Intent(MainActivity.this, AngebotVerwalten.class);
                startActivity(intent3);
                return true;

            case R.id.meineFavoritenAnsehenXML:
                Log.d("MyActivity", "Favoritenbutton");
                Intent intent6 = new Intent(MainActivity.this, FavoritenAnsehen.class);
                startActivity(intent6);
                return true;

            case R.id.profilXML:
                Log.d("MyActivity", "Profilbutton");
                Intent intent5 = new Intent(MainActivity.this, Profil.class);
                startActivity(intent5);
                return true;

            case R.id.logoutXML:
                Log.d("MyActivity", "Logoutbutton");
                SharedPreferences settings = this.getSharedPreferences("bereitsLogin", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent intent4 = new Intent(MainActivity.this, Login.class);
                startActivity(intent4);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void datenHerunterladen() {
        String ausgewählteKategorien = getIntent().getStringExtra("ausgewählteKategorien");
        Log.d("MyActivity", ausgewählteKategorien);
        DownloadData task = new DownloadData();
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
            String benutzername = sharedPreferences.getString("userID", "default");
            String url = "https://shareit.geschwister-scholl-gymnasium.de/datenAbrufen.php?ausgewählteKategorien=" + URLEncoder.encode(ausgewählteKategorien, "UTF-8");
            url = url + "&benutzername=" + URLEncoder.encode(benutzername, "UTF-8");
            Log.d("MyActivity", "URL: " + url);
            task.execute (url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("MyActivity", "Task executed");

        String result = null;

        try {
            result = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (result.equals("keine Internetverbindung")) {
            Intent intent = new Intent(this, KeineInternetverbindung.class);
            startActivity(intent);
            return;
        }

        try {
            InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
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
        }

        if (angebote.isEmpty()){
            Intent intent = new Intent(MainActivity.this, FehlermeldungKeineAngebote.class);
            startActivity(intent);
            finish();
        }
    }
}