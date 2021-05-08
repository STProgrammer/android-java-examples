package com.wfamedia.viewpager2utstyr0;

public class Utstyr {
    private String type;
    private String produsent;
    private String modell;
    private long innkjøpt;
    private char status; // (utlånt eller ikke)
    private String utlånt_til;
    private String bildeUrl;

    public Utstyr(String type, String produsent, String modell, long innkjøpt, char status, String utlånt_til, String bildeUrl) {
        this.type = type;
        this.produsent = produsent;
        this.modell = modell;
        this.innkjøpt = innkjøpt;
        this.status = status;
        this.utlånt_til = utlånt_til;
        this.bildeUrl = bildeUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProdusent() {
        return produsent;
    }

    public void setProdusent(String produsent) {
        this.produsent = produsent;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public long getInnkjøpt() {
        return innkjøpt;
    }

    public void setInnkjøpt(long innkjøpt) {
        this.innkjøpt = innkjøpt;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getUtlånt_til() {
        return utlånt_til;
    }

    public void setUtlånt_til(String utlånt_til) {
        this.utlånt_til = utlånt_til;
    }

    public String getBildeUrl() {
        return bildeUrl;
    }

    public void setBildeUrl(String bildeUrl) {
        this.bildeUrl = bildeUrl;
    }

    @Override
    public String toString() {
        return "Utstyr{" +
                "type='" + type + '\'' +
                ", produsent='" + produsent + '\'' +
                ", modell='" + modell + '\'' +
                ", innkjøpt=" + innkjøpt +
                ", status=" + status +
                ", utlånt_til='" + utlånt_til + '\'' +
                ", bildeUrl='" + bildeUrl + '\'' +
                '}';
    }
}
