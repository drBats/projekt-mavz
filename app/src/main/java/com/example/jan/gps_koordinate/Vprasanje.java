package com.example.jan.gps_koordinate;


import java.util.HashMap;
import java.util.List;

public class Vprasanje {
    private String vprasanje;
    private HashMap<String, String> odgovori;
    private String pravOdgovor;

    public Vprasanje(){}

    public Vprasanje(String vprasanje, HashMap<String, String> odgovori, String pravOdgovor){
        this.vprasanje = vprasanje;
        this.odgovori = odgovori;
        this.pravOdgovor = pravOdgovor;
    }

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
}
