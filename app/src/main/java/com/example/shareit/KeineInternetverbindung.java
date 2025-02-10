package com.example.shareit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class KeineInternetverbindung extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keine_internetverbindung);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mainXML);
        setSupportActionBar(toolbar);

        TextView textView1 = findViewById(R.id.text_view_platzhalter);
        TextView textView2 = findViewById(R.id.keine_internetverbindung);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.updateXML:
                Log.d("MyActivity", "Aktualisierungsbutton");
                String ausgewählteKategorien = "1, 2, 3, 4, 5";
                Intent intent = new Intent(KeineInternetverbindung.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);
                return true;

            case R.id.filtersymbolXML:
                Log.d("MyActivity", "Filterbutton");
                Intent intent1 = new Intent(KeineInternetverbindung.this, Filterfunktion.class);
                startActivity(intent1);
                return true;

            case R.id.angebotEinstellenXML:
                Log.d("MyActivity", "Einstellungsbutton");
                Intent intent2 = new Intent(KeineInternetverbindung.this, AngebotEinstellen.class);
                startActivity(intent2);
                return true;

            case R.id.angeboteVerwaltenXML:
                Log.d("MyActivity", "Verwaltungsbutton");
                Intent intent3 = new Intent(KeineInternetverbindung.this, AngebotVerwalten.class);
                startActivity(intent3);
                return true;

            case R.id.meineFavoritenAnsehenXML:
                Log.d("MyActivity", "Favoritenbutton");
                Intent intent6 = new Intent(KeineInternetverbindung.this, FavoritenAnsehen.class);
                startActivity(intent6);
                return true;

            case R.id.profilXML:
                Log.d("MyActivity", "Profilbutton");
                Intent intent5 = new Intent(KeineInternetverbindung.this, Profil.class);
                startActivity(intent5);
                return true;

            case R.id.logoutXML:
                Log.d("MyActivity", "Logoutbutton");
                SharedPreferences settings = this.getSharedPreferences("bereitsLogin", Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Intent intent4 = new Intent(KeineInternetverbindung.this, Login.class);
                startActivity(intent4);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}