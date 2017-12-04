package com.example.jan.gps_koordinate;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class OzivljanjeActivity extends AppCompatActivity {

    ImageButton btnOzivljanje;
    FloatingActionButton fab;
    TextView text, txtNatancnost;
    double time_now, time_before;
    double time_first=0, times_clicked=0;
    double BPM, tocno=0;
    int procenti;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozivljanje);

        btnOzivljanje = (ImageButton)findViewById(R.id.button_ress);
        text = (TextView)findViewById(R.id.textView2);
        txtNatancnost = (TextView)findViewById(R.id.textNatancnost);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        btnOzivljanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(time_first == 0){
                    //Začetni čas
                    time_first = System.currentTimeMillis();
                    time_before = System.currentTimeMillis();
                    times_clicked++;
                }
                else{
                    time_now=System.currentTimeMillis();
                    times_clicked++;
                    BPM = 60 / ((time_now - time_before)/1000);
                    if(BPM > 120 || BPM < 100){
                        text.setTextColor(Color.RED);
                        procenti = (int)((tocno/times_clicked)*100);
                        txtNatancnost.setText(String.valueOf(procenti) + "%");
                    }
                    else {
                        text.setTextColor(Color.GREEN);
                        tocno++;
                        procenti = (int)((tocno/times_clicked)*100);
                        txtNatancnost.setText(String.valueOf(procenti) + "%");
                    }
                    text.setText(String.valueOf((int)BPM));
                    time_before=time_now;
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OzivljanjeActivity.this, NavodilaActivity.class);
                i.putExtra("id", 1);
                startActivity(i);
            }
        });
    }
}
