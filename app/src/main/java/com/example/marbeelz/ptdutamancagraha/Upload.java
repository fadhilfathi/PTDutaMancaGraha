package com.example.marbeelz.ptdutamancagraha;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public Upload(){

    }

    public Upload(String mName, String mImageUrl) {
        if (mName.trim().equals("")){
            mName = "No Name";
        }
        this.mName = mName;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    @Exclude
    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public void setmName(String mName) {
        this.mName = mName;
    }
}
