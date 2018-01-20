package com.example.jan.gps_koordinate;

import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import java.lang.Math;

public class Pospeskometer extends AppCompatActivity implements SensorEventListener {

    TextView txt;
    TextView bpm;
    Sensor senzor;
    SensorManager manager;

    float[] gravitacija = new float[3];
    float[] pospesek = new float[3];
    float alpha = 0.3f;
    float amplituda;
    float poTime1=0, poTime2=0;

    int poz = 0;
    int prev = 0;

    double time_now, time_before;
    double time_first=0, times_clicked=0;
    double BPM, tocno=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pospeskometer);

        txt=(TextView)findViewById(R.id.txtAcc);
        bpm=(TextView)findViewById(R.id.textView10);
        manager=(SensorManager)getSystemService(SENSOR_SERVICE);
        senzor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        manager.registerListener(this, senzor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        gravitacija[0] = alpha * gravitacija[0] + (1 - alpha) * sensorEvent.values[0];
        gravitacija[1] = alpha * gravitacija[1] + (1 - alpha) * sensorEvent.values[1];
        gravitacija[2] = alpha * gravitacija[2] + (1 - alpha) * sensorEvent.values[2];

        pospesek[0] = sensorEvent.values[0] - gravitacija[0];
        pospesek[1] = sensorEvent.values[1] - gravitacija[1];
        pospesek[2] = sensorEvent.values[2] - gravitacija[2];

        txt.setText(
                "X: " + Math.round(pospesek[0] * 100.0) / 100.0 + "\n" +
                "Y: " + Math.round(pospesek[1] * 100.0) / 100.0 + "\n" +
                "Z: " + Math.round(pospesek[2] * 100.0) / 100.0 + "\n");

        amplituda = (float) Math.sqrt(
                        pospesek[0] * pospesek[0] +
                        pospesek[1] * pospesek[1] +
                        pospesek[2] * pospesek[2]);

       if(amplituda > 1){
           if(pospesek[2] < -0.4){
               poz = 1;
               poTime1 = System.currentTimeMillis();
               if(prev != poz){
                   BPM = 60 / ((poTime1 - poTime2)/1000);
                   bpm.setText(Double.toString(BPM));
               }
               prev=poz;
               poTime2 = poTime1;
           }
           else if(pospesek[2] > 0.4){
               prev = -1;
           }
       }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
