package com.example.jan.gps_koordinate;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class KvizActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase database;
    private DatabaseReference dbRef, data;

    private TextView textVprasanje;
    private Button odgA, odgB, odgC;

    private Vprasanje vprasanje;

    private int stVprasanj;
    private int trenutnoVprasanje = 0;
    private int stPravilnihOdgovorov = 0;

    private static int ST_VPRASANJ = 5;
    private static int ST_ODGOVOROV = 3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kviz);
        setTitle("Kviz");

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();

        textVprasanje = (TextView) findViewById(R.id.besedilo_vprasanja);
        odgA = (Button) findViewById(R.id.button_A);
        odgB = (Button) findViewById(R.id.button_B);
        odgC = (Button) findViewById(R.id.button_C);

        odgA.setOnClickListener(this);
        odgB.setOnClickListener(this);
        odgC.setOnClickListener(this);

        pripraviVprasanje(trenutnoVprasanje);
    }

    private void pripraviVprasanje(int indeks){
        data = dbRef.child(String.valueOf(indeks));

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vprasanje = dataSnapshot.getValue(Vprasanje.class);

                textVprasanje.setText(vprasanje.getVprasanje());
                odgA.setText(vprasanje.getOdgovori().get("a"));
                odgB.setText(vprasanje.getOdgovori().get("b"));
                odgC.setText(vprasanje.getOdgovori().get("c"));
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
        String odgovor = b.getText().toString();

        if(odgovor.equals(vprasanje.getOdgovori().get(vprasanje.getPravOdgovor()))){
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
