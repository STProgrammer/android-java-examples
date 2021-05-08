package com.wfamedia.viewpager2utstyr0;

import java.util.ArrayList;
import java.util.Date;

public class UtstyrsListe {
    private ArrayList<Utstyr> utstyr;

    public UtstyrsListe() {
        utstyr = new ArrayList<>();
        utstyr.add(new Utstyr("TLF","Samsung", "S11", new Date().getTime(), 'U', "Per Persen", "https://goo.gl/gEgYUd"));
        utstyr.add(new Utstyr("PC","Samsung", "S12", new Date().getTime(), '_', "Lise Persen", "https://i.imgur.com/DvpvklR.png"));
        utstyr.add(new Utstyr("IPAD","Samsung", "S13", new Date().getTime(), '_', "Per Persen", "https://kark.uit.no/~wfa004/d3330/images/playstation1.jpg"));
        utstyr.add(new Utstyr("ANDROID NETTBRETT","Samsung", "S14", new Date().getTime(), 'U', "Hanne Persen", "https://kark.uit.no/~wfa004/d3330/images/playstation1.jpg"));
        utstyr.add(new Utstyr("PLAYSTATION","Samsung", "S15", new Date().getTime(), 'U', "Bjørn Persen", "https://kark.uit.no/~wfa004/d3330/images/playstation1.jpg"));
    }

    public ArrayList<Utstyr> getUtstyr() {
        return utstyr;
    }

    public void setUtstyr(ArrayList<Utstyr> utstyr) {
        this.utstyr = utstyr;
    }

    public void addUstyr(Utstyr utstyr) {
        this.utstyr.add(utstyr);
    }
}
