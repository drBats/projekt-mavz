package com.example.jan.gps_koordinate;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GPS extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    private double latitude, longitude;
    private TextView mLatitude, mLongitude;
    private MapFragment mMapFragment;
    private Context mContext;
    private GoogleMap mMap;
    private boolean mUpdate;
    private Marker marker;

    public GPS(TextView latitude, TextView longitude, MapFragment mapFragment, Context context){
        mLatitude = latitude;
        mLongitude = longitude;

        mMapFragment = mapFragment;
        mContext=context;
        mUpdate = true;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        mLatitude.setText(Double.toString(latitude));
        mLongitude.setText(Double.toString(longitude));

        mMapFragment.getMapAsync(this);

    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLatitude(double lat)
    {
        latitude=lat;
    }

    public void setLongitude(double longti)
    {
        longitude=longti;
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng pos = new LatLng(latitude, longitude);
        if(marker!=null)
        {
            marker.remove();
        }
        marker=mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title("nasa lokacija")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        if(mUpdate) {
            mUpdate = false;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
        }
    }

    public void addMapMarker(double lat, double lng, String title){
        LatLng pos = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title));
        Log.i("addMapMarker", "Adding marker " + title + " at " + lat + ", " + lng);
    }
}
