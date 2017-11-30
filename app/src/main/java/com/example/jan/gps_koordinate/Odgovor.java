package com.example.jan.gps_koordinate;

import android.os.Parcel;
import android.os.Parcelable;

public class Odgovor implements Parcelable{
    private Vprasanje vprasanje;
    private String odgovor;

    public Odgovor(){}

    public Odgovor(Vprasanje vprasanje, String odgovor){

        this.vprasanje = vprasanje;
        this.odgovor = odgovor;
    }

    public Vprasanje getVprasanje(){
        return vprasanje;
    }

    public String getOdgovor(){
        return odgovor;
    }

    protected Odgovor(Parcel in) {
        vprasanje = in.readParcelable(Vprasanje.class.getClassLoader());
        odgovor = in.readString();
    }

    public static final Creator<Odgovor> CREATOR = new Creator<Odgovor>() {
        @Override
        public Odgovor createFromParcel(Parcel in) {
            return new Odgovor(in);
        }

        @Override
        public Odgovor[] newArray(int size) {
            return new Odgovor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(vprasanje, i);
        parcel.writeString(odgovor);
    }
}
