package com.example.lab4_2;

public class Utstyr {
    private String type;
    private String produsent;
    private String modell;
    private Long innkjoept;
    private char status;
    private String utlaantTil;
    private String bildeUrl;



    public Utstyr(String t, String p, String m, Long i, char s, String ut, String bu) {
        type = t;
        produsent = p;
        modell = m;
        innkjoept = i;
        status = s;
        utlaantTil = ut;
        bildeUrl = bu;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProdusent(String produsent) {
        this.produsent = produsent;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public void setInnkjoept(Long innkjoept) {
        this.innkjoept = innkjoept;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public void setUtlaantTil(String utlaantTil) {
        this.utlaantTil = utlaantTil;
    }

    public void setBildeUrl(String bildeUrl) {
        this.bildeUrl = bildeUrl;
    }

    public String getType() {
        return type;
    }

    public String getProdusent() {
        return produsent;
    }

    public String getModell() {
        return modell;
    }

    public Long getInnkjoept() {
        return innkjoept;
    }

    public char getStatus() {
        return status;
    }

    public String getUtlaantTil() {
        return utlaantTil;
    }

    public String getBildeUrl() {
        return bildeUrl;
    }
}
