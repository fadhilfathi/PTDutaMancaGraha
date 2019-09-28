package com.example.marbeelz.ptdutamancagraha;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mHarga;
    private String mAlamat;
    private String mLuas_Tanah;
    private String mLuas_Bangunan;
    private String mSumber_Air;
    private String mListrik;
    private String mKamarTidur;
    private String mKamarMandi;
    private String mGarasi;
    private String mCarport;
    private String mImageUrl;
    private String mStatus;
    private String mKey;

    public Upload(String mName, String mHarga, String mAlamat, String mLuas_Tanah, String mLuas_Bangunan, String mSumber_Air, String mListrik, String mKamarTidur, String mKamarMandi, String mGarasi, String mCarport, String mImageUrl, String mStatus) {
        this.mName = mName;
        this.mHarga = mHarga;
        this.mAlamat = mAlamat;
        this.mLuas_Tanah = mLuas_Tanah;
        this.mLuas_Bangunan = mLuas_Bangunan;
        this.mSumber_Air = mSumber_Air;
        this.mListrik = mListrik;
        this.mKamarTidur = mKamarTidur;
        this.mKamarMandi = mKamarMandi;
        this.mGarasi = mGarasi;
        this.mCarport = mCarport;
        this.mImageUrl = mImageUrl;
        this.mStatus = mStatus;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmHarga() {
        return mHarga;
    }

    public void setmHarga(String mHarga) {
        this.mHarga = mHarga;
    }

    public String getmAlamat() {
        return mAlamat;
    }

    public void setmAlamat(String mAlamat) {
        this.mAlamat = mAlamat;
    }

    public String getmLuas_Tanah() {
        return mLuas_Tanah;
    }

    public void setmLuas_Tanah(String mLuas_Tanah) {
        this.mLuas_Tanah = mLuas_Tanah;
    }

    public String getmLuas_Bangunan() {
        return mLuas_Bangunan;
    }

    public void setmLuas_Bangunan(String mLuas_Bangunan) {
        this.mLuas_Bangunan = mLuas_Bangunan;
    }

    public String getmSumber_Air() {
        return mSumber_Air;
    }

    public void setmSumber_Air(String mSumber_Air) {
        this.mSumber_Air = mSumber_Air;
    }

    public String getmListrik() {
        return mListrik;
    }

    public void setmListrik(String mListrik) {
        this.mListrik = mListrik;
    }

    public String getmKamarTidur() {
        return mKamarTidur;
    }

    public void setmKamarTidur(String mKamarTidur) {
        this.mKamarTidur = mKamarTidur;
    }

    public String getmKamarMandi() {
        return mKamarMandi;
    }

    public void setmKamarMandi(String mKamarMandi) {
        this.mKamarMandi = mKamarMandi;
    }

    public String getmGarasi() {
        return mGarasi;
    }

    public void setmGarasi(String mGarasi) {
        this.mGarasi = mGarasi;
    }

    public String getmCarport() {
        return mCarport;
    }

    public void setmCarport(String mCarport) {
        this.mCarport = mCarport;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public Upload() {

    }
}