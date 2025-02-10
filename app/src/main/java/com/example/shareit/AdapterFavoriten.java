package com.example.shareit;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdapterFavoriten extends RecyclerView.Adapter<AdapterFavoriten.Viewholder> {

    private Context context;
    private ArrayList<Angebot> angeboteArrayList;
    String favorisiert_aendern_feedback;

    //Konstruktor
    public AdapterFavoriten(Context context, ArrayList<Angebot> angeboteArrayList) {
        this.context = context;
        this.angeboteArrayList = angeboteArrayList;
    }

    @NonNull
    @Override
    public AdapterFavoriten.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_main, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavoriten.Viewholder holder, int position) {
        Angebot model = angeboteArrayList.get(position);
        holder.angebot_nameTV.setText(model.getAngebot_name());
        holder.person_nameTV.setText(model.getPerson_name());
        holder.angebot_beschreibungTV.setText(model.getAngebot_beschreibung());
        String favorisiert = model.getFavorisiert();
        if(favorisiert.equals("true")){
            holder.imageButtonStar.setImageResource(R.drawable.ic_baseline_star_24);
        } else {
            holder.imageButtonStar.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
    }

    @Override
    public int getItemCount() {
        return angeboteArrayList.size();
    }

    @SuppressWarnings("SpellCheckingInspection")
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView angebot_nameTV, angebot_beschreibungTV, person_nameTV;
        private ImageButton imageButtonStar;
        //private ImageButton imageButtonChat;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            person_nameTV = itemView.findViewById(R.id.personNameXML);
            angebot_nameTV = itemView.findViewById(R.id.angebotNameXML);
            angebot_beschreibungTV = itemView.findViewById(R.id.angebotBeschreibungXML);
            imageButtonStar = itemView.findViewById(R.id.imageButtonStar);
            imageButtonStar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    if (imageButtonStar.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.ic_baseline_star_24).getConstantState())) {
                        Log.d("MyActivity", "Stern ist ausgefüllt");

                        int position = getAdapterPosition();
                        Angebot betroffenesAngebot = angeboteArrayList.get(position);
                        String angebot_id = betroffenesAngebot.getAngebot_id();

                        SharedPreferences sharedPreferences = context.getSharedPreferences("bereitsLogin", MODE_PRIVATE);
                        String benutzername = sharedPreferences.getString("userID", "default");

                        FavorisiertAendernPHP task = new FavorisiertAendernPHP();

                        try {
                            String url = "https://shareit.geschwister-scholl-gymnasium.de/favorisiertLoeschen.php?angebot_id=" + URLEncoder.encode(angebot_id, "UTF-8");
                            url = url + "&benutzername=" + URLEncoder.encode(benutzername, "UTF-8");
                            Log.d("MyActivity", "URL: " + url);
                            task.execute(url);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Log.d("MyActivity", "Task executed");

                        try {
                            InputStream stream = new ByteArrayInputStream(task.get().getBytes(StandardCharsets.UTF_8));
                            InputStreamReader input = new InputStreamReader(stream, "UTF8");
                            BufferedReader reader = new BufferedReader(input, 2000);
                            favorisiert_aendern_feedback = reader.readLine();
                            favorisiert_aendern_feedback = favorisiert_aendern_feedback.toString();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.d("MyActivity", "favorisiert_aendern_feedback: " + favorisiert_aendern_feedback);

                        if (favorisiert_aendern_feedback.trim().equals("done")) {
                            Toast toast_true = Toast.makeText(context, "Das Angebot wurde aus deinen Favoriten entfernt.", Toast.LENGTH_LONG);
                            toast_true.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast_true.show();
                        } else {
                            if (favorisiert_aendern_feedback.trim().equals("keine Internetverbindung")) {
                                Toast toast_internet = Toast.makeText(context, "Du hast keine Internetverbindung.", Toast.LENGTH_LONG);
                                toast_internet.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast_internet.show();
                            } else {
                                Toast toast_false = Toast.makeText(context, "Das Angebot konnte leider nicht aus deinen Favoriten entfernt werden.", Toast.LENGTH_LONG);
                                toast_false.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast_false.show();
                            }
                        }

                        Intent intent = new Intent(context, FavoritenAnsehen.class);
                        context.startActivity(intent);

                    } else if (imageButtonStar.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.ic_baseline_star_border_24).getConstantState())) {
                        Log.d("MyActivity", "Stern ist nicht ausgefüllt");

                        int position = getAdapterPosition();
                        Angebot betroffenesAngebot = angeboteArrayList.get(position);
                        String angebot_id = betroffenesAngebot.getAngebot_id();

                        SharedPreferences sharedPreferences = context.getSharedPreferences("bereitsLogin", MODE_PRIVATE);
                        String benutzername = sharedPreferences.getString("userID", "default");

                        FavorisiertAendernPHP task = new FavorisiertAendernPHP();

                        try {
                            String url = "https://shareit.geschwister-scholl-gymnasium.de/favorisiertHinzufuegen.php?angebot_id=" + URLEncoder.encode(angebot_id, "UTF-8");
                            url = url + "&benutzername=" + URLEncoder.encode(benutzername, "UTF-8");
                            Log.d("MyActivity", "URL: " + url);
                            task.execute(url);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Log.d("MyActivity", "Task executed");

                        try {
                            InputStream stream = new ByteArrayInputStream(task.get().getBytes(StandardCharsets.UTF_8));
                            InputStreamReader input = new InputStreamReader(stream, "UTF8");
                            BufferedReader reader = new BufferedReader(input, 2000);
                            favorisiert_aendern_feedback = reader.readLine();
                            favorisiert_aendern_feedback = favorisiert_aendern_feedback.toString();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.d("MyActivity", "favorisiert_aendern_feedback: " + favorisiert_aendern_feedback);

                        if (favorisiert_aendern_feedback.trim().equals("done")) {
                            Toast toast_true = Toast.makeText(context, "Das Angebot wurde zu deinen Favoriten hinzugefügt.", Toast.LENGTH_LONG);
                            toast_true.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast_true.show();
                        } else {
                            if (favorisiert_aendern_feedback.trim().equals("keine Internetverbindung")) {
                                Toast toast_internet = Toast.makeText(context, "Du hast keine Internetverbindung.", Toast.LENGTH_LONG);
                                toast_internet.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast_internet.show();
                            } else {
                                Toast toast_false = Toast.makeText(context, "Das Angebot konnte leider nicht zu deinen Favoriten hinzugefügt werden.", Toast.LENGTH_LONG);
                                toast_false.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast_false.show();
                            }
                        }

                        Intent intent = new Intent(context, FavoritenAnsehen.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}