package com.example.jan.gps_koordinate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OzivljanjeActivity extends AppCompatActivity {

    Button btnOzivljanje;
    TextView text;
    double time_presssed,time_now, end_time, time_before;
    double time_first=0, times_clicked=0;
    double BPM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozivljanje);

        btnOzivljanje = (Button)findViewById(R.id.button_ress);
        text = (TextView)findViewById(R.id.textView2);

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
                    text.setText(String.valueOf((int)BPM));
                    time_before=time_now;
                }
            }
        });
    }
}
