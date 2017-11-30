package com.example.jan.gps_koordinate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RezultatiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultati);
        setTitle("Rezultati");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            int stPravilnihOdgovorov = bundle.getInt("stPravilnihOdgovorov");
            int stVprasanj = bundle.getInt("stVprasanj");
            ArrayList<Odgovor> odgovori = bundle.getParcelableArrayList("odgovori");

            for(int i = 0; i < odgovori.size(); i++){
                Vprasanje vprasanje = odgovori.get(i).getVprasanje();

                View view = getLayoutInflater().inflate(R.layout.layout_odgovor, null);
                ((TextView) view.findViewById(R.id.vprasanje)).setText(vprasanje.toString());
                ((TextView) view.findViewById(R.id.odgovor_a)).setText(vprasanje.getOdgovori().get("a") + (odgovori.get(i).getOdgovor().equals("a") ? " <--" : ""));
                ((TextView) view.findViewById(R.id.odgovor_b)).setText(vprasanje.getOdgovori().get("b") + (odgovori.get(i).getOdgovor().equals("b") ? " <--" : ""));
                ((TextView) view.findViewById(R.id.odgovor_c)).setText(vprasanje.getOdgovori().get("c") + (odgovori.get(i).getOdgovor().equals("c") ? " <--" : ""));
                ((LinearLayout) findViewById(R.id.seznam_odgovorov)).addView(view);
            }

            Toast.makeText(this, "Vaš rezultat: " + Math.round(((float)stPravilnihOdgovorov / stVprasanj) * 100) + "%", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Na voljo še ni nobenih rezultatov.", Toast.LENGTH_LONG).show();
        }

    }

}
