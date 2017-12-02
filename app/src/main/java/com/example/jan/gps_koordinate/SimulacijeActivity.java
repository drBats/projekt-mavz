package com.example.jan.gps_koordinate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SimulacijeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulacije);

        Button btnDefibrilator = (Button)findViewById(R.id.button_defibrilator);
        Button btnOzivljanje = (Button)findViewById(R.id.button_ozivljanje);

        btnDefibrilator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SimulacijeActivity.this, DefibrilatorActivity.class);
                startActivity(i);
            }
        });

        btnOzivljanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SimulacijeActivity.this, OzivljanjeActivity.class);
                startActivity(i);
            }
        });
    }
}
