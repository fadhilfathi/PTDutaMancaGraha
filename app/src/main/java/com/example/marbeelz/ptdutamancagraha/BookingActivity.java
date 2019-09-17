package com.example.marbeelz.ptdutamancagraha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import maes.tech.intentanim.CustomIntent;

public class BookingActivity extends AppCompatActivity {
    private Button button;
    View layout1, layout2, layout3;
    private Spinner spinnerPendidikan, spinnerStatusHubungan, spinnerStatusRumah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookinglayout);
        Toolbar toolbar = findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");
//        button = findViewById(R.id.btn_next);
//        layout1 = findViewById(R.id.lay1);
//        layout2 = findViewById(R.id.lay2);
//        layout3 = findViewById(R.id.lay3);
        String [] pendidikan =
                {"Pendidikan","SD","SMP","SMA","Diploma", "S1","S2 / S3"};
        String [] statushubungan=
                {"Status","Menikah","Lajang","Duda", "Janda"};
        String [] statusrumah =
                {"Status Rumah", "Sendiri","Kontrak","Keluarga","Dinas"};


        spinnerPendidikan = findViewById(R.id.spinner_pendidikan);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pendidikan);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerPendidikan.setAdapter(adapter);

        spinnerStatusHubungan = findViewById(R.id.spinner_status_hubungan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statushubungan);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerStatusHubungan.setAdapter(adapter1);

        spinnerStatusRumah = findViewById(R.id.spinner_status_rumah);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusrumah);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerStatusRumah.setAdapter(adapter2);



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (layout1.getVisibility() == View.VISIBLE && layout2.getVisibility() == View.GONE && layout3.getVisibility() == View.GONE) {
//                   layout1.setVisibility(View.GONE);
//                   layout2.setVisibility(View.VISIBLE);
//                } else if
//                    (layout1.getVisibility() == View.GONE&& layout2.getVisibility() == View.VISIBLE && layout3.getVisibility() == View.GONE){
//                    layout2.setVisibility(View.GONE);
//                    layout3.setVisibility(View.VISIBLE);
//                } else if (layout1.getVisibility() == View.GONE&& layout2.getVisibility() == View.GONE && layout3.getVisibility() == View.VISIBLE) {
//                    Intent x = new Intent(getApplicationContext(), HomeActivity.class);
//                    startActivity(x);
//                }
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        CustomIntent.customType(this, "right-to-left");
        super.onBackPressed();
    }


}
