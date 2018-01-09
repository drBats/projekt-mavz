package com.example.jan.gps_koordinate;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.LOCATION_SERVICE;

public class ZemljevidFragment extends Fragment {

    private Button gumb, getHospitals;
    private TextView tekst, latitude, longitude;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private GPS gps;
    private Location lokacija;
    private String bestProvider;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_zemljevid, container, false);

        gumb = (Button) rootView.findViewById(R.id.button);
        getHospitals = (Button) rootView.findViewById(R.id.getHospitals);
        tekst = (TextView) rootView.findViewById(R.id.textView);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        latitude = (TextView) rootView.findViewById(R.id.latitude);
        longitude = (TextView) rootView.findViewById(R.id.longitude);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        gps = new GPS(latitude, longitude, mapFragment, getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
            return null;
        } else {
            configureButton();
        }
        bestProvider=locationManager.getBestProvider(new Criteria(), true);
        lokacija = locationManager.getLastKnownLocation(bestProvider);

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == getActivity().getPackageManager().PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void configureButton() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gumb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                try {
                    if(lokacija != null){
                        latitude.setText(Double.toString(lokacija.getLatitude()));
                        longitude.setText(Double.toString(lokacija.getLongitude()));
                        gps.setLatitude(lokacija.getLatitude());
                        gps.setLongitude(lokacija.getLongitude());
                        lokacija=null;
                    }
                    else
                    {
                        tekst.setText("Pridobivanje podatkov...");
                    }
                    locationManager.requestLocationUpdates("network", 0, 0, gps);
                }
                catch (SecurityException e)
                {
                    tekst.setText("Napaka pri pridobivanju podatkov...");
                }
                tekst.setText("");
            }
        });

        getHospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String result = new GetHospitalsTask().execute().get();
                    Log.i("GetHospitalsTask$result", result);

                    JSONObject jObject = new JSONObject(result);
                    JSONArray jArray = jObject.getJSONArray("results");

                    for (int i = 0; i < jArray.length(); i++)
                    {
                        try {
                            JSONObject oneObject = jArray.getJSONObject(i);

                            JSONObject geometry = oneObject.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");

                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");

                            String name = oneObject.getString("name");

                            gps.addMapMarker(lat, lng, name);
                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                }
                catch(Exception ex){

                }

            }
        });
    }

    class GetHospitalsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                String key = "AIzaSyBGEFNgdIKVz6fuPSnQ8ArtMtWIeEW82qo";
                URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + key + "&location=" + gps.getLatitude() + "," + gps.getLongitude() + "&rankby=distance&type=hospital");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }
}
