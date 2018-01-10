package com.example.jan.gps_koordinate;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;

public class Vprasanje implements Parcelable, Serializable{
    private int id;
    private String vprasanje;
    private String kategorija;
    private HashMap<String, String> odgovori;
    private String težavnost;
    private String pravOdgovor;

    public Vprasanje(){ }

    public Vprasanje(String vprasanje, String kategorija, HashMap<String, String> odgovori, String težavnost, String pravOdgovor){
        this.vprasanje = vprasanje;
        this.kategorija = kategorija;
        this.odgovori = odgovori;
        this.težavnost = težavnost;
        this.pravOdgovor = pravOdgovor;
    }

    protected Vprasanje(Parcel in) {
        id = in.readInt();
        vprasanje = in.readString();
        kategorija = in .readString();
        odgovori = in.readHashMap(String.class.getClassLoader());
        težavnost = in.readString();
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

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getVprasanje() {
        return vprasanje;
    }

    public String getKategorija() {
        return kategorija;
    }

    public HashMap<String, String> getOdgovori() {
        return odgovori;
    }

    public String getOdgovor(String key){
        return odgovori.get(key);
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
        parcel.writeInt(id);
        parcel.writeString(vprasanje);
        parcel.writeString(kategorija);
        parcel.writeMap(odgovori);
        parcel.writeString(težavnost);
        parcel.writeString(pravOdgovor);
    }

    public String getTežavnost() {
        return težavnost;
    }
}
