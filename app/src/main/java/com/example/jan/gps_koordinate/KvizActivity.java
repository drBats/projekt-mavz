package com.example.jan.gps_koordinate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class KvizActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference dbRef, data;

    private TextView textVprasanje;
    private Button odgA, odgB, odgC;

    private Vprasanje vprasanje;
    private ArrayList<Integer> zaporedjeVprasanj;
    private ArrayList<Odgovor> odgovori;

    private int trenutnoVprasanje = 0;
    private int stPravilnihOdgovorov = 0;

    private static int ST_VPRASANJ_SKUPAJ = 60;
    private static int ST_VPRASANJ_KVIZ = 20;

    private long startTime, endTime;
    private boolean started;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kviz);
        setTitle("Kviz");

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("vprasanja");

        textVprasanje = (TextView) findViewById(R.id.besedilo_vprasanja);
        odgA = (Button) findViewById(R.id.button_A);
        odgB = (Button) findViewById(R.id.button_B);
        odgC = (Button) findViewById(R.id.button_C);

        odgA.setOnClickListener(this);
        odgB.setOnClickListener(this);
        odgC.setOnClickListener(this);

        Random rnd = new Random();
        zaporedjeVprasanj = new ArrayList<>();
        for(int i = 0; i < ST_VPRASANJ_KVIZ; i++){
            int n = rnd.nextInt(ST_VPRASANJ_SKUPAJ);
            while(zaporedjeVprasanj.contains(n)) n = rnd.nextInt(ST_VPRASANJ_SKUPAJ);
            zaporedjeVprasanj.add(n);
        }

        odgovori = new ArrayList<>();

        started = false;
        pripraviVprasanje(trenutnoVprasanje);
    }

    private void pripraviVprasanje(final int indeks){
        data = dbRef.child(String.valueOf(zaporedjeVprasanj.get(indeks)));

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vprasanje = dataSnapshot.getValue(Vprasanje.class);
                vprasanje.setId(zaporedjeVprasanj.get(indeks));

                textVprasanje.setText(vprasanje.getVprasanje());
                odgA.setText(vprasanje.getOdgovori().get("a"));
                odgB.setText(vprasanje.getOdgovori().get("b"));
                odgC.setText(vprasanje.getOdgovori().get("c"));

                if (!started) startTime = System.nanoTime();
                started = true;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("onDataChange", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        String odgovor = (String)b.getTag();

        odgovori.add(new Odgovor(vprasanje, odgovor, endTime - startTime));

        if(odgovor.equals(vprasanje.getPravOdgovor())){
            stPravilnihOdgovorov++;
        }

        if(trenutnoVprasanje == ST_VPRASANJ_KVIZ - 1){
            endTime = System.nanoTime();

            RezultatiKviza rezultati = new RezultatiKviza(odgovori, ST_VPRASANJ_KVIZ, stPravilnihOdgovorov, ST_VPRASANJ_KVIZ - stPravilnihOdgovorov, endTime - startTime);
            Bundle bundle = new Bundle();
            bundle.putParcelable("rezultati", rezultati);

            Intent intent = new Intent(this, RezultatiActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            pripraviVprasanje(++trenutnoVprasanje);
        }

    }
}
