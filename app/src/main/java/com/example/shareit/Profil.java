package com.example.shareit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);

        ImageButton buttonHome = (ImageButton) findViewById(R.id.imageButtonHome2);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ausgewählteKategorien = "1, 2, 3, 4, 5";
                Intent intent = new Intent(Profil.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("bereitsLogin", MODE_PRIVATE);
        String userSharedPref = sharedPreferences.getString("user", "default");
        String vornameSharedPref = sharedPreferences.getString("vorname", "default");
        String nachnameSharedPref = sharedPreferences.getString("nachname", "default");
        String klasseSharedPref = sharedPreferences.getString("klasse", "default");

        TextView textViewBeNa = (TextView) findViewById(R.id.benutzernameToChange);
        textViewBeNa.setText(userSharedPref);

        TextView textViewVoNa = (TextView) findViewById(R.id.vornameToChange);
        textViewVoNa.setText(vornameSharedPref);

        TextView textViewNaNa = (TextView) findViewById(R.id.nachnameToChange);
        textViewNaNa.setText(nachnameSharedPref);

        TextView textViewK = (TextView) findViewById(R.id.klasseToChange);
        textViewK.setText(klasseSharedPref);
    }
}