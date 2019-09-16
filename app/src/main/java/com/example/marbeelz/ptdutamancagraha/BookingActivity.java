package com.example.marbeelz.ptdutamancagraha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import maes.tech.intentanim.CustomIntent;

public class BookingActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference booking;
    private Button button;
    private EditText NamaLengkap, NomorKTP, TempatLahir, TanggalLahir, Alamat, Blok, NomorRumah, RT, RW, Kelurahan, Kecamatan,
            Kota, Provinsi, KodePos, SesuaiKTP, NoTelp, NoTelpRumah, Email, Pendidikan, StatusHubungan, StatusRumah, LamaRumahDitempati;
    private EditText NamaPerusahaan, Jabatan, LamaMenjabat, MasaKerjaTotal, AlamatPerusahaan, BlokPerusahaan, NomorPerusahaan,
            RTPerusahaan, RWPerusahaan, KelurahanPerusahaan, KecamatanPerusahaan, KotaPerusahaan, ProvinsiPerusahaan, KodePosPerusahaan,
            NamaAtasan, NoTelpAtasan, NoTelpKantor;
    private EditText NamaKeluarga, HubunganKeluarga, AlamatKeluarga, BlokKeluarga, NomorKeluarga, RTKeluarga, RWKeluarga, KelurahanKeluarga,
            KecamatanKeluarga, KotaKeluarga, ProvinsiKeluarga, KodePosKeluarga;
    private SwitchCompat switchSesuaiKTP;
    View layout1, layout2, layout3;
    private Spinner spinnerPendidikan, spinnerStatusHubungan, spinnerStatusRumah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookinglayout);
        Toolbar toolbar = findViewById(R.id.toolbarx);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");
        button = findViewById(R.id.btn_next);
        layout1 = findViewById(R.id.lay1);
        layout2 = findViewById(R.id.lay2);
        layout3 = findViewById(R.id.lay3);
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

        NamaLengkap = findViewById(R.id.nama);
        NomorKTP = findViewById(R.id.no_ktp);
        TempatLahir = findViewById(R.id.tempatlahir);
        TanggalLahir = findViewById(R.id.tanggalLahir);
        Alamat = findViewById(R.id.alamatRumah);
        Blok = findViewById(R.id.blok);
        NomorRumah = findViewById(R.id.nomor);
        RT = findViewById(R.id.RT);
        RW = findViewById(R.id.RW);
        Kelurahan = findViewById(R.id.kelurahan);
        Kecamatan = findViewById(R.id.kecamatan);
        Kota = findViewById(R.id.kota);
        Provinsi = findViewById(R.id.provinsi);
        KodePos = findViewById(R.id.kodepos);
        switchSesuaiKTP = findViewById(R.id.SesuaiKTP);
        NoTelp = findViewById(R.id.No_Handphone);
        NoTelpRumah = findViewById(R.id.telepon_rumah);
        Email = findViewById(R.id.email);
        Pendidikan = findViewById(R.id.pendidikan);
        spinnerStatusHubungan = findViewById(R.id.spinner_status_hubungan);
        spinnerStatusRumah = findViewById(R.id.spinner_status_rumah);
        LamaRumahDitempati = findViewById(R.id.lamaDitempati);

        NamaPerusahaan = findViewById(R.id.tempatBekerja);
        Jabatan = findViewById(R.id.jabatan);
        LamaMenjabat = findViewById(R.id.lamaMenjabat);
        MasaKerjaTotal = findViewById(R.id.masaKerja);
        AlamatPerusahaan = findViewById(R.id.alamatKantor);
        BlokPerusahaan = findViewById(R.id.blok);
        NomorPerusahaan = findViewById(R.id.nomor);
        RTPerusahaan = findViewById(R.id.RT);
        RWPerusahaan = findViewById(R.id.RW);
        KelurahanPerusahaan = findViewById(R.id.kelurahan);
        KecamatanPerusahaan = findViewById(R.id.kecamatan);
        KotaPerusahaan = findViewById(R.id.kota);
        ProvinsiPerusahaan = findViewById(R.id.provinsi);
        KodePosPerusahaan = findViewById(R.id.kodepos);
        NoTelpAtasan = findViewById(R.id.No_Handphone_atasan);
        NoTelpKantor = findViewById(R.id.telepon_kantor);
        NamaAtasan = findViewById(R.id.namaAtasan);

        NamaKeluarga = findViewById(R.id.namaKeluarga);
        HubunganKeluarga = findViewById(R.id.hubunganKeluarga);
        AlamatKeluarga = findViewById(R.id.alamatKeluarga);
        BlokKeluarga = findViewById(R.id.blokKeluarga);
        NomorKeluarga = findViewById(R.id.nomorKeluarga);
        RTKeluarga = findViewById(R.id.RTKeluarga);
        RWKeluarga = findViewById(R.id.RWKeluarga);
        KelurahanKeluarga = findViewById(R.id.kelurahanKeluarga);
        KecamatanKeluarga = findViewById(R.id.kecamatanKeluarga);
        KotaKeluarga = findViewById(R.id.kotaKeluarga);
        ProvinsiKeluarga = findViewById(R.id.provinsiKeluarga);
        KodePosKeluarga = findViewById(R.id.kodeposKeluarga);

        booking = database.getReference("Booking");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String namaLengkap = NamaLengkap.getText().toString().trim();
                final String nomorKTP = NomorKTP.getText().toString().trim();
                final String tempatLahir = TempatLahir.getText().toString().trim();
                final String tanggalLahir = TanggalLahir.getText().toString().trim();
                final String alamat = Alamat.getText().toString().trim();
                final String blok = Blok.getText().toString().trim();
                final String nomorRumah = NomorRumah.getText().toString().trim();
                final String rT = RT.getText().toString().trim();
                final String rW = RW.getText().toString().trim();
                final String kelurahan = Kelurahan.getText().toString().trim();
                final String kecamatan = Kecamatan.getText().toString().trim();
                final String kota = Kota.getText().toString().trim();
                final String provinsi = Provinsi.getText().toString().trim();
                final String kodePos = KodePos.getText().toString().trim();
                final String ssesuaiKTP = SesuaiKTP.getText().toString().trim();
                final String noTelp = NoTelp.getText().toString().trim();
                final String noTelpRumah = NoTelpRumah.getText().toString().trim();
                final String email = Email.getText().toString().trim();
                final String pendidikan = Pendidikan.getText().toString().trim();
                final String statusHubungan = StatusHubungan.getText().toString().trim();
                final String statusRumah = StatusRumah.getText().toString().trim();
                final String lamaRumahDitempati = LamaRumahDitempati.getText().toString().trim();

                final String namaPerusahaan = NamaPerusahaan.getText().toString().trim();
                final String jabatan = Jabatan.getText().toString().trim();
                final String lamaMenjabat = LamaMenjabat.getText().toString().trim();
                final String masaKerjaTotal = MasaKerjaTotal.getText().toString().trim();
                final String alamatPerusahaan = AlamatPerusahaan.getText().toString().trim();
                final String blokPerusahaan = BlokPerusahaan.getText().toString().trim();
                final String nomorPerusahaan = NomorPerusahaan.getText().toString().trim();
                final String rTPerusahaan = RTPerusahaan.getText().toString().trim();
                final String rWPerusahaan = RWPerusahaan.getText().toString().trim();
                final String kelurahanPerusahaan = KelurahanPerusahaan.getText().toString().trim();
                final String kecamatanPerusahaan = KecamatanPerusahaan.getText().toString().trim();
                final String kotaPerusahaan = KotaPerusahaan.getText().toString().trim();
                final String provinsiPerusahaan = ProvinsiPerusahaan.getText().toString().trim();
                final String kodePosPerusahaan = KodePosPerusahaan.getText().toString().trim();
                final String namaAtasan = NamaAtasan.getText().toString().trim();
                final String noTelpAtasan = NoTelpAtasan.getText().toString().trim();
                final String noTelpKantor = NoTelpKantor.getText().toString().trim();

                final String namaKeluarga = NamaKeluarga.getText().toString().trim();
                final String hubunganKeluarga = HubunganKeluarga.getText().toString().trim();
                final String alamatKeluarga = AlamatKeluarga.getText().toString().trim();
                final String blokKeluarga = BlokKeluarga.getText().toString().trim();
                final String nomorKeluarga = NomorKeluarga.getText().toString().trim();
                final String rTKeluarga = RTKeluarga.getText().toString().trim();
                final String rWKeluarga = RWKeluarga.getText().toString().trim();
                final String kelurahanKeluarga = KelurahanKeluarga.getText().toString().trim();
                final String kecamatanKeluarga = KecamatanKeluarga.getText().toString().trim();
                final String kotaKeluarga = KotaKeluarga.getText().toString().trim();
                final String provinsiKeluarga = ProvinsiKeluarga.getText().toString().trim();
                final String kodePosKeluarga = KodePosKeluarga.getText().toString().trim();

//                booking.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Booking books = new Booking(namaLengkap, nomorKTP, tempatLahir, tanggalLahir, alamat, blok, nomorRumah, rT, rW, kelurahan, kecamatan, kota, provinsi,
//                                kodePos, ssesuaiKTP, noTelp, noTelpRumah, email, pendidikan, statusHubungan, statusRumah, lamaRumahDitempati, namaPerusahaan, jabatan, lamaMenjabat,
//                                masaKerjaTotal, alamatPerusahaan, blokPerusahaan, nomorPerusahaan, rTPerusahaan, rWPerusahaan, kelurahanPerusahaan, kecamatanPerusahaan, kotaPerusahaan,
//                                provinsiPerusahaan, kodePosPerusahaan, namaAtasan, noTelpAtasan, noTelpKantor, namaKeluarga, hubunganKeluarga, alamatKeluarga, blokKeluarga, nomorKeluarga,
//                                rTKeluarga, rWKeluarga, kelurahanKeluarga, kecamatanKeluarga, kotaKeluarga, provinsiKeluarga, kodePosKeluarga);
//                        booking.child(namaLengkap).setValue(books);
//                        Toast.makeText(BookingActivity.this, "Booking Telah Berhasil, Silahkan Lanjutkan ke Proses Selanjutnya", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
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
