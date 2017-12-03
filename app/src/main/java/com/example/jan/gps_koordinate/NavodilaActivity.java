package com.example.jan.gps_koordinate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NavodilaActivity extends AppCompatActivity {

    TextView txtNaslov, txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navodila2);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");

        txtNaslov=(TextView)findViewById(R.id.textViewNaslov);
        txtText=(TextView)findViewById(R.id.textViewText);

        switch(id){
            case 1:
                txtNaslov.setText("Oživljanje");
                txtText.setText(R.string.ozivljanje);
        }


    }
}
