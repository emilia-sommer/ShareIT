package com.example.shareit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Filterfunktion extends AppCompatActivity {

    public String ausgewählteKategorien = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters);

        Button buttonKategorien = (Button) findViewById(R.id.buttonFilters);
        buttonKategorien.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                CheckBox checkBox_Bücher = findViewById(R.id.checkBox_Bücher);
                if(checkBox_Bücher.isChecked()){
                   ausgewählteKategorien = "1";
                }

                CheckBox checkBox_Zeitschriften = findViewById(R.id.checkBox_Zeitschriften);
                if(checkBox_Zeitschriften.isChecked()){
                    if (ausgewählteKategorien == null){
                        ausgewählteKategorien = "2";
                    }
                    else {
                        ausgewählteKategorien = ausgewählteKategorien + ", 2";
                    }
                }

                CheckBox checkBox_CDs = findViewById(R.id.checkBox_CDs);
                if(checkBox_CDs.isChecked()){
                    if (ausgewählteKategorien == null){
                        ausgewählteKategorien = "3";
                    }
                    else {
                        ausgewählteKategorien = ausgewählteKategorien + ", 3";
                    }
                }

                CheckBox checkBox_DVDs = findViewById(R.id.checkBox_DVDs);
                if(checkBox_DVDs.isChecked()){
                    if (ausgewählteKategorien == null){
                        ausgewählteKategorien = "4";
                    }
                    else {
                        ausgewählteKategorien = ausgewählteKategorien + ", 4";
                    }
                }

                CheckBox checkBox_Sonstiges = findViewById(R.id.checkBox_Sonstiges);
                if(checkBox_Sonstiges.isChecked()){
                    if (ausgewählteKategorien == null){
                        ausgewählteKategorien = "5";
                    }
                    else {
                        ausgewählteKategorien = ausgewählteKategorien + ", 5";
                    }
                }

                if (ausgewählteKategorien == null){
                    //Intent intent = new Intent(Filterfunktion.this, FehlermeldungKategorieauswahl.class);
                    //startActivity(intent);
                    Toast toast = Toast.makeText(Filterfunktion.this, "Du musst mindestens eine Kategorie auswählen.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                else{
                    Intent intent = new Intent(Filterfunktion.this, MainActivity.class);
                    intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                    startActivity(intent);
                }
            }
        });

        Button buttonAlle = (Button) findViewById(R.id.buttonAlleHerunterladen);
        buttonAlle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ausgewählteKategorien = "1, 2, 3, 4, 5";
                Intent intent = new Intent(Filterfunktion.this, MainActivity.class);
                intent.putExtra("ausgewählteKategorien", ausgewählteKategorien);
                startActivity(intent);
            }
        });

    }
}