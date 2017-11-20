package com.example.jan.gps_koordinate;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class KvizActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView vprasanje;
    private Button odgA, odgB, odgC;

    private Random rnd;

    private static int ST_VPRASANJ = 5;
    private static int ST_ODGOVOROV = 3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kviz);
        setTitle("Kviz");

        vprasanje = (TextView) findViewById(R.id.besedilo_vprasanja);
        odgA = ((Button) findViewById(R.id.button_A));
        odgB = ((Button) findViewById(R.id.button_B));
        odgC = ((Button) findViewById(R.id.button_C));

        odgA.setOnClickListener(this);
        odgB.setOnClickListener(this);
        odgC.setOnClickListener(this);

        rnd = new Random();
        int indeks = rnd.nextInt(ST_VPRASANJ);

        pripraviVprasanje(indeks);
    }

    private void pripraviVprasanje(int indeks){
        String trenutnoVprasanje = this.getResources().getStringArray(R.array.vprasanja)[indeks];
        String odgovorA = this.getResources().getStringArray(R.array.odgovori)[indeks * ST_ODGOVOROV];
        String odgovorB = this.getResources().getStringArray(R.array.odgovori)[indeks * ST_ODGOVOROV + 1];
        String odgovorC = this.getResources().getStringArray(R.array.odgovori)[indeks * ST_ODGOVOROV + 2];

        vprasanje.setText(trenutnoVprasanje);
        odgA.setText(odgovorA);
        odgB.setText(odgovorB);
        odgC.setText(odgovorC);
    }

    @Override
    public void onClick(View v) {
        pripraviVprasanje(rnd.nextInt(ST_VPRASANJ));
    }
}
