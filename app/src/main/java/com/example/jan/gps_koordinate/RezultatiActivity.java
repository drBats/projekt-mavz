package com.example.jan.gps_koordinate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RezultatiActivity extends AppCompatActivity {

    private TextView textOdgovori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultati);
        setTitle("Rezultati");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        textOdgovori = (TextView) findViewById(R.id.odgovori);

        if(bundle != null){
            int stPravilnihOdgovorov = bundle.getInt("stPravilnihOdgovorov");
            int stVprasanj = bundle.getInt("stVprasanj");
            ArrayList<Odgovor> odgovori = bundle.getParcelableArrayList("odgovori");

            for(int i = 0; i < odgovori.size(); i++){
                Vprasanje vprasanje = odgovori.get(i).getVprasanje();
                textOdgovori.append(vprasanje + ": " + vprasanje.getOdgovor(odgovori.get(i).getOdgovor()) + "\n");
            }

            Toast.makeText(this, "Vaš rezultat: " + Math.round(((float)stPravilnihOdgovorov / stVprasanj) * 100) + "%", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Na voljo še ni nobenih rezultatov.", Toast.LENGTH_LONG).show();
        }

    }

}
