package com.example.shareit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterVerwaltung extends RecyclerView.Adapter<AdapterVerwaltung.Viewholder> {

    private Context context;
    private ArrayList<Angebot> angeboteArrayList;

    //Konstruktor
    public AdapterVerwaltung(Context context, ArrayList<Angebot> angeboteArrayList) {
        this.context = context;
        this.angeboteArrayList = angeboteArrayList;
    }

    @NonNull
    @Override
    public AdapterVerwaltung.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_verwaltung, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVerwaltung.Viewholder holder, int position) {
        Angebot model = angeboteArrayList.get(position);
        holder.angebot_nameTV.setText(model.getAngebot_name());
        holder.person_nameTV.setText(model.getPerson_name());
        holder.angebot_beschreibungTV.setText(model.getAngebot_beschreibung());
    }

    @Override
    public int getItemCount() {
        return angeboteArrayList.size();
    }

    @SuppressWarnings("SpellCheckingInspection")
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView angebot_nameTV, angebot_beschreibungTV, person_nameTV;
        private ImageButton imageButtonMuell;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            person_nameTV = itemView.findViewById(R.id.personNameXML);
            angebot_nameTV = itemView.findViewById(R.id.angebotNameXML);
            angebot_beschreibungTV = itemView.findViewById(R.id.angebotBeschreibungXML);
            imageButtonMuell = itemView.findViewById(R.id.imageButtonMuell);

            imageButtonMuell.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Angebot betroffenesAngebot = angeboteArrayList.get(position);
                        werteBetroffenesAngebot(betroffenesAngebot);
                    }
                }
            });
        }

        private void werteBetroffenesAngebot(Angebot betroffenesAngebot){
            String titel_betroffenes_angebot = betroffenesAngebot.getAngebot_name();
            String beschreibung_betroffenes_angebot = betroffenesAngebot.getAngebot_beschreibung();
            Log.d("MyActivity", "betroffenesAngebot (titel): " + titel_betroffenes_angebot);
            Log.d("MyActivity", "betroffenesAngebot (beschreibung): " + beschreibung_betroffenes_angebot);

            //Intent intent = new Intent(AdapterVerwaltung.this.context, AngebotLoeschen.class);
            Intent intent = new Intent(context, AngebotVerwaltenWartemeldung.class);
            intent.putExtra("titel_betroffenes_angebot", titel_betroffenes_angebot);
            intent.putExtra("beschreibung_betroffenes_angebot", beschreibung_betroffenes_angebot);
            context.startActivity(intent);
        }
    }
}