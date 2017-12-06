package com.example.jan.gps_koordinate;

import android.os.Parcel;
import android.os.Parcelable;

public class Odgovor implements Parcelable{
    private Vprasanje vprasanje;
    private String odgovor;
    private long cas;

    public Odgovor(){}

    public Odgovor(Vprasanje vprasanje, String odgovor, long cas){

        this.vprasanje = vprasanje;
        this.odgovor = odgovor;
        this.cas = cas;
    }

    public Vprasanje getVprasanje(){
        return vprasanje;
    }

    public String getOdgovor(){
        return odgovor;
    }

    public long getCas() { return cas; }

    protected Odgovor(Parcel in) {
        vprasanje = in.readParcelable(Vprasanje.class.getClassLoader());
        odgovor = in.readString();
        cas = in.readLong();
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
        parcel.writeLong(cas);
    }
}
