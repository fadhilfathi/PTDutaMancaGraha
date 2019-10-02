package com.example.marbeelz.ptdutamancagraha;

import android.content.SharedPreferences;

public class Booking {
    private String KeyRumah;
    private String JudulRumah;
    private String NamaPembeli;
    private String NoHp;
    private String Agen;
    private String mStatusBooking;
    private String mImageUrl;
    private String mKey;

    public Booking() {
    }

    public Booking(String keyRumah, String judulRumah, String namaPembeli, String noHp, String agen, String mStatusBooking, String mImageUrl) {
        KeyRumah = keyRumah;
        JudulRumah = judulRumah;
        NamaPembeli = namaPembeli;
        NoHp = noHp;
        Agen = agen;
        this.mStatusBooking = mStatusBooking;
        this.mImageUrl = mImageUrl;
    }

    public String getKeyRumah() {
        return KeyRumah;
    }

    public void setKeyRumah(String keyRumah) {
        KeyRumah = keyRumah;
    }

    public String getJudulRumah() {
        return JudulRumah;
    }

    public void setJudulRumah(String judulRumah) {
        JudulRumah = judulRumah;
    }

    public String getNamaPembeli() {
        return NamaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        NamaPembeli = namaPembeli;
    }

    public String getNoHp() {
        return NoHp;
    }

    public void setNoHp(String noHp) {
        NoHp = noHp;
    }

    public String getAgen() {
        return Agen;
    }

    public void setAgen(String agen) {
        Agen = agen;
    }

    public String getmStatusBooking() {
        return mStatusBooking;
    }

    public void setmStatusBooking(String mStatusBooking) {
        this.mStatusBooking = mStatusBooking;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
