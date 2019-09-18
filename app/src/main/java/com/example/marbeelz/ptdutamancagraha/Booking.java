package com.example.marbeelz.ptdutamancagraha;

public class Booking {
    private String Rumah;
    private String NamaPembeli;
    private String NoHp;
    private String mImageUrl;
    private String mKey;

    public Booking(String rumah, String namaPembeli, String noHp, String mImageUrl) {
        Rumah = rumah;
        NamaPembeli = namaPembeli;
        NoHp = noHp;
        this.mImageUrl = mImageUrl;
    }

    public String getRumah() {
        return Rumah;
    }

    public void setRumah(String rumah) {
        Rumah = rumah;
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
