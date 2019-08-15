package com.example.marbeelz.ptdutamancagraha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import maes.tech.intentanim.CustomIntent;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);
    }

    @Override
    public void onBackPressed() {
        CustomIntent.customType(this, "right-to-left");
        super.onBackPressed();
    }
}
