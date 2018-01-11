package com.example.jan.gps_koordinate;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class RezultatiFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    RezultatiKviza rezultati;
    ArrayList<Odgovor> odgovori;
    int stVprasanj, stPravilnihOdgovorov;
    boolean rezultatiNaVoljo = false;
    FragmentManager fragmentManager;

    public RezultatiFragment(){}

    @Override
    public void setArguments(Bundle args) {
        rezultati = args.getParcelable("rezultati");
        stPravilnihOdgovorov = rezultati.getStPravilnih();
        stVprasanj = rezultati.getStVprasanj();
        odgovori = rezultati.getOdgovori();

        rezultatiNaVoljo = true;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();

    }

    @Override
    public void onDetach() {
        NujnaPomocFragment fragment = new NujnaPomocFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rezultati, container, false);

        Date currentTime;

        if(rezultatiNaVoljo){
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Log.w("permissionRationale", "true");

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            File file = new File(path, "podatki.arff");

            FileOutputStream outputStream;
            if(!file.exists()){
                try{
                    if(!file.createNewFile()){
                        Toast.makeText(getActivity(), "Napaka pri ustvarjanju datoteke.", Toast.LENGTH_LONG).show();
                        return null;
                    }

                    outputStream = new FileOutputStream(file);
                    outputStream.write(getString(R.string.arff_header).getBytes());

                } catch(IOException ex){
                    Log.w("RezultatiFragment", "Error writing " + file, ex);
                }

            }
            try {
                currentTime = Calendar.getInstance().getTime(); //dobimo čas reševanja
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD H:mm");
                FileOutputStream fos = getActivity().openFileOutput(formatter.format(currentTime) + " " + Math.round(((float) stPravilnihOdgovorov / stVprasanj) * 100) + "%", Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(rezultati);
                os.close();
                fos.close();
            }catch(IOException ex){
                Log.w("json", "Error writing " + file, ex);
            }
            //View view = inflater.inflate(R.layout.fragment_rezultati, null);
            int procenti_resevanja=Math.round(((float)stPravilnihOdgovorov / stVprasanj) * 100);
            ((TextView) rootView.findViewById(R.id.pravilnost_resevanja)).setText("VAS PROCENT RESEVANJA: "+procenti_resevanja+"%");
            if(procenti_resevanja<50) {
                ((TextView) rootView.findViewById(R.id.pravilnost_resevanja)).setTextColor(Color.RED);
            } else if (procenti_resevanja<70){
                ((TextView) rootView.findViewById(R.id.pravilnost_resevanja)).setTextColor(Color.rgb(255,165,0));
            } else {
                ((TextView) rootView.findViewById(R.id.pravilnost_resevanja)).setTextColor(Color.GREEN);
            }
            try{
                outputStream = new FileOutputStream(file, true);
                for(int i = 0; i < odgovori.size(); i++) {
                    Vprasanje vprasanje = odgovori.get(i).getVprasanje();

                    View view = inflater.inflate(R.layout.layout_odgovor, null);
                    ((TextView) view.findViewById(R.id.vprasanje)).setText(i+1+". "+vprasanje.toString());
                    ((TextView) view.findViewById(R.id.odgovor_a)).setText(vprasanje.getOdgovori().get("a"));
                    if((odgovori.get(i).getVprasanje().getPravOdgovor().equals("a")))
                    {
                        ((TextView) view.findViewById(R.id.odgovor_a)).setTextColor(Color.GREEN);
                    }
                    else if(odgovori.get(i).getOdgovor().equals("a"))
                    {
                        ((TextView) view.findViewById(R.id.odgovor_a)).setTextColor(Color.RED);
                    }
                    ((TextView) view.findViewById(R.id.odgovor_b)).setText(vprasanje.getOdgovori().get("b"));
                    if((odgovori.get(i).getVprasanje().getPravOdgovor().equals("b")))
                    {
                        ((TextView) view.findViewById(R.id.odgovor_b)).setTextColor(Color.GREEN);
                    }
                    else if(odgovori.get(i).getOdgovor().equals("b"))
                    {
                        ((TextView) view.findViewById(R.id.odgovor_b)).setTextColor(Color.RED);
                    }
                    ((TextView) view.findViewById(R.id.odgovor_c)).setText(vprasanje.getOdgovori().get("c"));
                    if((odgovori.get(i).getVprasanje().getPravOdgovor().equals("c")))
                    {
                        ((TextView) view.findViewById(R.id.odgovor_c)).setTextColor(Color.GREEN);
                    }
                    else if(odgovori.get(i).getOdgovor().equals("c"))
                    {
                        ((TextView) view.findViewById(R.id.odgovor_c)).setTextColor(Color.RED);
                    }

                    /*((TextView) view.findViewById(R.id.odgovor_a)).setText(vprasanje.getOdgovori().get("a") + (odgovori.get(i).getOdgovor().equals("a") ? " <--" : ""));
                    ((TextView) view.findViewById(R.id.odgovor_b)).setText(vprasanje.getOdgovori().get("b") + (odgovori.get(i).getOdgovor().equals("b") ? " <--" : ""));
                    ((TextView) view.findViewById(R.id.odgovor_c)).setText(vprasanje.getOdgovori().get("c") + (odgovori.get(i).getOdgovor().equals("c") ? " <--" : ""));*/
                    ((LinearLayout) rootView.findViewById(R.id.seznam_odgovorov)).addView(view);
                }

                String vrstica = rezultati.getStVprasanj() + ", " + rezultati.getStPravilnih() + ", " + rezultati.getStNepravilnih() + ", " + rezultati.getAvgKategorija() + ", " + rezultati.getDiscCasResevanja() + ", " + rezultati.getAvgTezavnost() + ", " + rezultati.klasificiraj() + "\n";
                outputStream.write(vrstica.getBytes());
                outputStream.close();


            } catch(IOException ex){
                Log.w("ExternalStorage", "Error writing " + file, ex);
            }

            //TODO: prikaži rezultat
            Toast.makeText(getActivity(), "Vaš rezultat: " + Math.round(((float)stPravilnihOdgovorov / stVprasanj) * 100) + "%", Toast.LENGTH_LONG).show(); //procenti
        }
        else{
            Toast.makeText(getActivity(), "Na voljo še ni nobenih rezultatov.", Toast.LENGTH_LONG).show();
        }


        return rootView;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
