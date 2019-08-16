package com.example.marbeelz.ptdutamancagraha;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FormFragment extends Fragment {
    @Nullable
    private ProgressBar mProgressBar;
    private Context mContext;

    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private StorageReference mStorageRef;

    private EditText nama,noKtp,tempatLahir,tanggalLahir,alamatRumah,blok,nomor,RT,RW,kelurahan,kecamatan,kota,provinsi,kodePos,noHP,noTelp,email;

    private Spinner spinnerPendidikan, spinnerStatusHubungan;
    private SwitchCompat sesuaiKTP;
    private ImageButton next;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Daftar Rumah");
        final View view = inflater.inflate(R.layout.booking, container, false);
        String [] pendidikan =
                {"Pendidikan","SD","SMP","SMA","Diploma", "S1","S2 / S3"};
        String [] statushubungan=
                {"Status","Menikah","Belum Menikah","Duda", "Janda"};
        String [] statusrumah =
                {"Status Rumah", "Sendiri","Kontrak","Keluarga","Dinas"};

        spinnerPendidikan = view.findViewById(R.id.spinner_pendidikan);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, pendidikan);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerPendidikan.setAdapter(adapter);

        spinnerStatusHubungan = view.findViewById(R.id.spinner_status_hubungan);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, statushubungan);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerStatusHubungan.setAdapter(adapter1);

        nama = view.findViewById(R.id.nama);
        noKtp = view.findViewById(R.id.no_ktp);
        tempatLahir = view.findViewById(R.id.tempatlahir);
        tanggalLahir = view.findViewById(R.id.tanggalLahir);
        alamatRumah = view.findViewById(R.id.alamatRumah);
        blok = view.findViewById(R.id.blok);
        nomor = view.findViewById(R.id.nomor);
        RT = view.findViewById(R.id.RT);
        RW = view.findViewById(R.id.RW);
        kelurahan = view.findViewById(R.id.kelurahan);
        kecamatan = view.findViewById(R.id.kecamatan);
        kota = view.findViewById(R.id.kota);
        provinsi = view.findViewById(R.id.provinsi);
        kodePos = view.findViewById(R.id.kodepos);
        noHP = view.findViewById(R.id.No_Handphone);
        noTelp = view.findViewById(R.id.telepon_rumah);
        email = view.findViewById(R.id.email);
        sesuaiKTP = view.findViewById(R.id.SesuaiKTP);
//        next = view.findViewById(R.id.intro_btn_next);
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FormFragment2 formFragment2 = new FormFragment2();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
//                        R.anim.enter_left_to_right,R.anim.exit_left_to_right).replace(R.id.fragment_container,formFragment2).
//                        addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
//            }
//        });

        return view;
    }
}