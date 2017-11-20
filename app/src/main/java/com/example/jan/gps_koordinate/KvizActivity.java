package com.example.jan.gps_koordinate;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class KvizActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView vprasanje;
    private Button odgA, odgB, odgC;

    private int trenutnoVprasanje = 0;
    private int stPravilnihOdgovorov = 0;

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

        pripraviVprasanje(trenutnoVprasanje);
    }

    private void pripraviVprasanje(int indeks){
        String trenutnoVprasanje = this.getResources().getStringArray(R.array.vprasanja)[indeks];
        String odgovorA = this.getResources().getStringArray(R.array.mozni_odgovori)[indeks * ST_ODGOVOROV];
        String odgovorB = this.getResources().getStringArray(R.array.mozni_odgovori)[indeks * ST_ODGOVOROV + 1];
        String odgovorC = this.getResources().getStringArray(R.array.mozni_odgovori)[indeks * ST_ODGOVOROV + 2];

        vprasanje.setText(trenutnoVprasanje);
        odgA.setText(odgovorA);
        odgB.setText(odgovorB);
        odgC.setText(odgovorC);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        String odgovor = b.getText().toString();

        if(odgovor.equals(getResources().getStringArray(R.array.resitve)[trenutnoVprasanje])){
            stPravilnihOdgovorov++;
        }

        if(trenutnoVprasanje == ST_VPRASANJ - 1){
            Toast.makeText(this, "Vaš rezultat: " + Math.round(((float)stPravilnihOdgovorov / ST_VPRASANJ) * 100) + "%", Toast.LENGTH_LONG).show();
            trenutnoVprasanje = 0;
            stPravilnihOdgovorov = 0;

            pripraviVprasanje(trenutnoVprasanje);
        }
        else{
            pripraviVprasanje(++trenutnoVprasanje);
        }

    }
}
