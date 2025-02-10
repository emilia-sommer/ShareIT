package com.example.shareit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FehlermeldungKeineAngebote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fehlermeldung_keine_angebote);

        Button button = (Button) findViewById(R.id.button_fehlermeldung_keine_angebote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FehlermeldungKeineAngebote.this, Filterfunktion.class);
                startActivity(intent);

            }
        });
    }
}