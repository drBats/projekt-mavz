package com.example.jan.gps_koordinate;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;

public class Vprasanje implements Parcelable{
    private String vprasanje;
    private HashMap<String, String> odgovori;
    private String pravOdgovor;

    public Vprasanje(){}

    public Vprasanje(String vprasanje, HashMap<String, String> odgovori, String pravOdgovor){
        this.vprasanje = vprasanje;
        this.odgovori = odgovori;
        this.pravOdgovor = pravOdgovor;
    }

    protected Vprasanje(Parcel in) {
        vprasanje = in.readString();
        odgovori = in.readHashMap(String.class.getClassLoader());
        pravOdgovor = in.readString();
    }

    public static final Creator<Vprasanje> CREATOR = new Creator<Vprasanje>() {
        @Override
        public Vprasanje createFromParcel(Parcel in) {
            return new Vprasanje(in);
        }

        @Override
        public Vprasanje[] newArray(int size) {
            return new Vprasanje[size];
        }
    };

    public String getVprasanje() {
        return vprasanje;
    }

    public HashMap<String, String> getOdgovori() {
        return odgovori;
    }

    public String getPravOdgovor() {
        return pravOdgovor;
    }

    public String toString(){
        return vprasanje;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(vprasanje);
        parcel.writeMap(odgovori);
        parcel.writeString(pravOdgovor);
    }
}
