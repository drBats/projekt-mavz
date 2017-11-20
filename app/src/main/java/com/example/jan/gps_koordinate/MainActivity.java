package com.example.jan.gps_koordinate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button btnKviz, btnZemljevid, btnDefibrilator, btnRezultati;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnKviz = (Button) findViewById(R.id.button_kviz);
        btnZemljevid = (Button) findViewById(R.id.button_zemljevid);
        btnDefibrilator = (Button) findViewById(R.id.button_defibrilator);
        btnRezultati = (Button) findViewById(R.id.button_rezultati);

        btnKviz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kviz = new Intent(MainActivity.this, KvizActivity.class);
                startActivity(kviz);
            }
        });

        btnZemljevid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zemljevid = new Intent(MainActivity.this, ZemljevidActivity.class);
                startActivity(zemljevid);
            }
        });

        btnDefibrilator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent defibrilator = new Intent(MainActivity.this, DefibrilatorActivity.class);
                startActivity(defibrilator);
            }
        });

        btnRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rezultati = new Intent(MainActivity.this, rezultati.class);
                startActivity(rezultati);
            }
        });

    }
}
