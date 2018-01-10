package com.example.jan.gps_koordinate;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RezultatiKviza implements Parcelable, Serializable{
    private ArrayList<Odgovor> odgovori;
    private int stVprasanj, stPravilnih, stNepravilnih;
    private long casResevanja;

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

    public String getDiscCasResevanja(){
        if(casResevanja < minutesToNanoseconds(15)){
            return "zelo_hitro";
        }
        else if(casResevanja >= minutesToNanoseconds(15) && casResevanja < minutesToNanoseconds(20)){
            return "hitro";
        }
        else if(casResevanja >= minutesToNanoseconds(20) && casResevanja < minutesToNanoseconds(25)){
            return "srednje";
        }
        else if(casResevanja >= minutesToNanoseconds(25) && casResevanja < minutesToNanoseconds(30)){
            return "pocasi";
        }
        else if(casResevanja >= minutesToNanoseconds(30)){
            return "zelo_pocasi";
        }

        return "napaka";
    }

    private long minutesToNanoseconds(int minute){ //kao brez decimalk - 2,5 minute daš 25 not
        return minute * 6000000000L;
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

    private int tockeVprasanja(){
        float del = stVprasanj / 5;

        if(stPravilnih < del){
            return 5;
        }
        else if(stPravilnih >= del && stPravilnih < 2 * del){
            return 4;
        }
        else if(stPravilnih >= 2 * del && stPravilnih < 3 * del){
            return 3;
        }
        else if(stPravilnih >= 3 * del && stPravilnih < 4 * del){
            return 2;
        }
        else if(stPravilnih >= 4 * del){
            return 1;
        }

        throw new ArrayIndexOutOfBoundsException();
    }

    private int tockeCasResevanja(){
        String casResevanja = getDiscCasResevanja();

        switch (casResevanja){
            case "zelo_hitro":
                return 1;
            case "hitro":
                return 2;
            case "srednje":
                return 3;
            case "pocasi":
                return 4;
            case "zelo_pocasi":
                return 5;
        }

        throw new IllegalArgumentException(casResevanja);
    }

    private int tockeTezavnost(){
        String avgTezavnost = getAvgTezavnost();

        switch (avgTezavnost){
            case "zelo_lahko":
                return 1;
            case "lahko":
                return 2;
            case "srednje":
                return 3;
            case "težko":
                return 4;
            case "zelo_težko":
                return 5;
        }

        throw new IllegalArgumentException(avgTezavnost);
    }

    public String klasificiraj(){
        int tocke = tockeCasResevanja() + tockeTezavnost() + tockeVprasanja();

        if(tocke >= 3 && tocke < 5){
            return "zelo_lahek";
        }
        else if(tocke >= 5 && tocke < 8){
            return "lahek";
        }
        else if(tocke >= 8 && tocke < 11){
            return "srednje";
        }
        else if(tocke >= 11 && tocke < 14){
            return "težek";
        }
        else if(tocke >= 14 && tocke < 16){
            return "zelo_težek";
        }

        throw new IndexOutOfBoundsException(tocke + "");

    }

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
