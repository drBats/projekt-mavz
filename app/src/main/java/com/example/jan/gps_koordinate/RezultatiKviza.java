package com.example.jan.gps_koordinate;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RezultatiKviza implements Parcelable{
    public ArrayList<Odgovor> getOdgovori() {
        return odgovori;
    }

    public int getStVprasanj() {
        return stVprasanj;
    }

    public int getStPravilnih() {
        return stPravilnih;
    }

    public int getStNepravilnih() {
        return stNepravilnih;
    }

    public long getCasResevanja() {
        return casResevanja;
    }

    public String getAvgKategorija(){
        HashMap<String, Integer> kategorije = new HashMap<>();
        kategorije.put("Oprema", 0);
        kategorije.put("Oživljanje", 0);
        kategorije.put("Poškodbe", 0);
        kategorije.put("Zastrupitev", 0);
        kategorije.put("OdprteRane", 0);
        kategorije.put("Opekline", 0);

        for (Odgovor odgovor : odgovori) {
            String kategorija = odgovor.getVprasanje().getKategorija();
            int count = kategorije.get(kategorija);
            kategorije.put(kategorija, count + 1);
        }

        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : kategorije.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();

    }

    public String getAvgTezavnost(){
        HashMap<String, Integer> tezavnosti = new HashMap<>();
        tezavnosti.put("težko", 3);
        tezavnosti.put("srednje", 2);
        tezavnosti.put("lahko", 1);

        int skupnaTezavnost = 0;
        for (Odgovor odgovor : odgovori) {
            String tezavnost = odgovor.getVprasanje().getTežavnost();
            int count = tezavnosti.get(tezavnost);
            skupnaTezavnost += count;
        }

        if(skupnaTezavnost >= 20 && skupnaTezavnost < 26){
            return "zelo_lahko";
        }
        else if(skupnaTezavnost >= 26 && skupnaTezavnost < 35){
            return "lahko";
        }
        else if(skupnaTezavnost >= 35 && skupnaTezavnost < 45){
            return "srednje";
        }
        else if(skupnaTezavnost >= 45 && skupnaTezavnost < 55){
            return "težko";
        }
        else if(skupnaTezavnost >= 55 && skupnaTezavnost <= 60){
            return "zelo_težko";
        }

        return "napaka";

    }

    private ArrayList<Odgovor> odgovori;
    private int stVprasanj, stPravilnih, stNepravilnih;
    private long casResevanja;

    public RezultatiKviza() {}

    public RezultatiKviza(ArrayList<Odgovor> odgovori, int stVprasanj, int stPravilnih, int stNepravilnih, long casResevanja) {
        this.odgovori = odgovori;
        this.stVprasanj = stVprasanj;
        this.stPravilnih = stPravilnih;
        this.stNepravilnih = stNepravilnih;
        this.casResevanja = casResevanja;
    }

    protected RezultatiKviza(Parcel in) {
        odgovori = in.createTypedArrayList(Odgovor.CREATOR);
        stVprasanj = in.readInt();
        stPravilnih = in.readInt();
        stNepravilnih = in.readInt();
        casResevanja = in.readLong();
    }

    public static final Creator<RezultatiKviza> CREATOR = new Creator<RezultatiKviza>() {
        @Override
        public RezultatiKviza createFromParcel(Parcel in) {
            return new RezultatiKviza(in);
        }

        @Override
        public RezultatiKviza[] newArray(int size) {
            return new RezultatiKviza[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(odgovori);
        parcel.writeInt(stVprasanj);
        parcel.writeInt(stPravilnih);
        parcel.writeInt(stNepravilnih);
        parcel.writeLong(casResevanja);
    }
}
