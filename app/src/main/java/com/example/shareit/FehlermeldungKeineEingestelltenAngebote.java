package com.example.shareit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FehlermeldungKeineEingestelltenAngebote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fehlermeldung_keine_eingestellten_angebote);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button = (Button) findViewById(R.id.button_neues_angebot_einstellen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FehlermeldungKeineEingestelltenAngebote.this, AngebotEinstellen.class);
                startActivity(intent);

            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton buttonHome = (ImageButton) findViewById(R.id.imageButtonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ausgewählteKategorien = "1, 2, 3, 4, 5";
                Intent intent = new Intent(FehlermeldungKeineEingestelltenAngebote.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);
            }
        });
    }
}