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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FormFragment2 extends Fragment {
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
    private TextView namaTop;
    private EditText tempatBekerja, jabatan, lamaMenjabat, masaKerja, alamatKantor, blok,nomor,RT,RW,kelurahan,kecamatan,kota,provinsi
            ,kodePos,noHPAtasan,noTelpKantor,namaAtasan;
    private ImageButton next;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Daftar Rumah");
        final View view = inflater.inflate(R.layout.booking2, container, false);
        namaTop = view.findViewById(R.id.namaTop);
        tempatBekerja = view.findViewById(R.id.tempatBekerja);
        jabatan = view.findViewById(R.id.jabatan);
        lamaMenjabat = view.findViewById(R.id.lamaMenjabat);
        masaKerja = view.findViewById(R.id.masaKerja);
        alamatKantor = view.findViewById(R.id.alamatKantor);
        blok = view.findViewById(R.id.blok);
        nomor = view.findViewById(R.id.nomor);
        RT = view.findViewById(R.id.RT);
        RW = view.findViewById(R.id.RW);
        kelurahan = view.findViewById(R.id.kelurahan);
        kecamatan = view.findViewById(R.id.kecamatan);
        kota = view.findViewById(R.id.kota);
        provinsi = view.findViewById(R.id.provinsi);
        kodePos = view.findViewById(R.id.kodepos);
        noHPAtasan = view.findViewById(R.id.No_Handphone_atasan);
        noTelpKantor = view.findViewById(R.id.telepon_kantor);
        namaAtasan = view.findViewById(R.id.namaAtasan);
//        next = view.findViewById(R.id.intro_btn_next);
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FormFragment3 formFragment3 = new FormFragment3();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
//                        R.anim.enter_left_to_right,R.anim.exit_left_to_right).replace(R.id.fragment_container,formFragment3).
//                        addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
//            }
//        });

        return view;
    }
}