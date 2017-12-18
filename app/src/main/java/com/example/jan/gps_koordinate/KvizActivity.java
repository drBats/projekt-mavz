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

    private static int ST_VPRASANJ = 13;

    private long startTime, endTime;

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
        for(int i = 0; i < ST_VPRASANJ; i++){
            int n = rnd.nextInt(ST_VPRASANJ);
            while(zaporedjeVprasanj.contains(n)) n = rnd.nextInt(ST_VPRASANJ);
            zaporedjeVprasanj.add(n);
        }

        odgovori = new ArrayList<>();

        pripraviVprasanje(trenutnoVprasanje);
    }

    private void pripraviVprasanje(int indeks){
        data = dbRef.child(String.valueOf(zaporedjeVprasanj.get(indeks)));

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vprasanje = dataSnapshot.getValue(Vprasanje.class);

                textVprasanje.setText(vprasanje.getVprasanje());
                odgA.setText(vprasanje.getOdgovori().get("a"));
                odgB.setText(vprasanje.getOdgovori().get("b"));
                odgC.setText(vprasanje.getOdgovori().get("c"));

                startTime = System.nanoTime();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("onDataChange", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onClick(View v) {
        endTime = System.nanoTime();

        Button b = (Button)v;
        String odgovor = (String)b.getTag();

        odgovori.add(new Odgovor(vprasanje, odgovor, endTime - startTime));

        if(odgovor.equals(vprasanje.getPravOdgovor())){
            stPravilnihOdgovorov++;
        }

        if(trenutnoVprasanje == ST_VPRASANJ - 1){
            Bundle bundle = new Bundle();
            bundle.putInt("stPravilnihOdgovorov", stPravilnihOdgovorov);
            bundle.putInt("stVprasanj", ST_VPRASANJ);
            bundle.putParcelableArrayList("odgovori", odgovori);

            Intent intent = new Intent(this, RezultatiActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            pripraviVprasanje(++trenutnoVprasanje);
        }

    }
}
