package com.example.jan.gps_koordinate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class RezultatiActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultati);
        setTitle("Rezultati");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            int stPravilnihOdgovorov = bundle.getInt("stPravilnihOdgovorov");
            int stVprasanj = bundle.getInt("stVprasanj");
            ArrayList<Odgovor> odgovori = bundle.getParcelableArrayList("odgovori");

            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Log.w("permissionRationale", "true");

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
            File file = new File(path, "podatki.arff");

            try {
                path.mkdirs();
                file.createNewFile();

                outputStream = new FileOutputStream(file);
                outputStream.write("@relation kviz-rezultati\n\n@attribute vprasanje numeric\n@attribute kategorija {1, 2, 3, 4}\n@attribute cas-razmisljanja numeric\n\n@data\n".getBytes());

            } catch (IOException e) {
                // Unable to create file, likely because external storage is
                // not currently mounted.
                Log.w("ExternalStorage", "Error writing " + file, e);
            }

            for(int i = 0; i < odgovori.size(); i++){
                Vprasanje vprasanje = odgovori.get(i).getVprasanje();

                View view = getLayoutInflater().inflate(R.layout.layout_odgovor, null);
                ((TextView) view.findViewById(R.id.vprasanje)).setText(vprasanje.toString());
                ((TextView) view.findViewById(R.id.odgovor_a)).setText(vprasanje.getOdgovori().get("a") + (odgovori.get(i).getOdgovor().equals("a") ? " <--" : ""));
                ((TextView) view.findViewById(R.id.odgovor_b)).setText(vprasanje.getOdgovori().get("b") + (odgovori.get(i).getOdgovor().equals("b") ? " <--" : ""));
                ((TextView) view.findViewById(R.id.odgovor_c)).setText(vprasanje.getOdgovori().get("c") + (odgovori.get(i).getOdgovor().equals("c") ? " <--" : ""));
                ((LinearLayout) findViewById(R.id.seznam_odgovorov)).addView(view);

                String vrstica = i + "," + "1," + odgovori.get(i).getCas() + "\n";
                try{
                    outputStream.write(vrstica.getBytes());
                } catch(IOException ex){
                    Log.w("ExternalStorage", "Error writing " + file, ex);
                }
            }

            try{
                outputStream.close();
            } catch(IOException ex){
                Log.w("ExternalStorage", "Error writing " + file, ex);
            }

            Toast.makeText(this, "Vaš rezultat: " + Math.round(((float)stPravilnihOdgovorov / stVprasanj) * 100) + "%", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Na voljo še ni nobenih rezultatov.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.w("permissionsResult", "granted");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
