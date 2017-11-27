package com.example.jan.gps_koordinate;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PopUp extends AppCompatActivity {

    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");

        video = (VideoView)findViewById(R.id.videoView);

        //PopUp prikaže navodila za uporabo AED
        if(id == 1){
            video.setVideoPath("android.resource://"+ getPackageName()+"/" + R.raw.aed);
            video.start();
        }
    }
}
