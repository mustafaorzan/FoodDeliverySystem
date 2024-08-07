package com.example.fooddeliverysystem;

import android.view.Menu;

public class MenuModel {
    private String urunAdi, mutfak, aciklama;
    private int fiyat;

    public MenuModel(){}

    public MenuModel(String urunAdi, String mutfak, String aciklama, int fiyat) {
        this.urunAdi = urunAdi;
        this.mutfak = mutfak;
        this.aciklama = aciklama;
        this.fiyat = fiyat;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getMutfak() {
        return mutfak;
    }

    public void setMutfak(String mutfak) {
        this.mutfak = mutfak;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }
}
