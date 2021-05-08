package com.example.lab4_2;

import java.util.ArrayList;
import java.util.Date;

public class UtstyrsListe {

    ArrayList<Utstyr> utstyrListe;


    public UtstyrsListe() {
        utstyrListe = new ArrayList<>();
        utstyrListe.add(new Utstyr("TLF","Samsung", "S11", new Date().getTime(), 'U', "Per Persen", "https://goo.gl/gEgYUd"));
        utstyrListe.add(new Utstyr("PC","Samsung", "S12", new Date().getTime(), 'U', "Lise Persen", "https://i.imgur.com/DvpvklR.png"));
        utstyrListe.add(new Utstyr("IPAD","Samsung", "S13", new Date().getTime(), '_', "Per Persen", "https://kark.uit.no/~wfa004/d3330/images/playstation1.jpg"));
        utstyrListe.add(new Utstyr("ANDROID NETTBRETT","Samsung", "S14", new Date().getTime(), 'U', "Hanne Persen", "https://kark.uit.no/~wfa004/d3330/images/playstation1.jpg"));
        utstyrListe.add(new Utstyr("PLAYSTATION","Samsung", "S15", new Date().getTime(), 'U', "Bjørn Persen", "https://kark.uit.no/~wfa004/d3330/images/playstation1.jpg"));
    }

    public void add(Utstyr uts) {
        utstyrListe.add(uts);
    }

    public Utstyr getUtstyr(int index) {
        return utstyrListe.get(index);
    }


    public ArrayList<Utstyr> getUtstyrListe() {
        return utstyrListe;
    }

    public void setUtstyrListe(ArrayList<Utstyr> utstyrListe) {
        this.utstyrListe = utstyrListe;
    }


}
