package com.example.marbeelz.ptdutamancagraha;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AfterBooking extends Fragment {
    Button button;
    TextView textView1, textView2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.after_booking, container, false);
        String nama = getArguments().getString("Nama");
        String nohp = getArguments().getString("NoHp");
        textView1 = view.findViewById(R.id.text1);
        textView2 = view.findViewById(R.id.text2);
        button = view.findViewById(R.id.button);

        textView1.setText("Atas Nama : " + nama);
        textView2.setText("Nomor HP : " + nohp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                UserFragment_1 userFragment_1 = new UserFragment_1();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,userFragment_1).addToBackStack(null).commit();
            }
        });

        return view;
    }
}