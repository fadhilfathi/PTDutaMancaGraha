package com.example.marbeelz.ptdutamancagraha;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.zip.Inflater;

public class FilterDialogUser extends DialogFragment {
    private EditText editTextJudul, hargamin, hargamax;
    private Button filterCari, filterCancel;
    RadioGroup radioGroup;
    RadioButton tersedia, telahdibooking, tidaktersedia, semua, radioButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog, container, false);

        editTextJudul = view.findViewById(R.id.filterJudul);
        hargamin = view.findViewById(R.id.filterhargamin);
        hargamax = view.findViewById(R.id.filterhargamax);
        radioGroup = view.findViewById(R.id.radiogrup);
        tersedia = view.findViewById(R.id.radiotersedia);
        telahdibooking = view.findViewById(R.id.radiotelahdibooking);
        tidaktersedia = view.findViewById(R.id.radiotidaktersedia);
        semua = view.findViewById(R.id.radiosemua);
        filterCancel = view.findViewById(R.id.filterCancel);
        filterCari = view.findViewById(R.id.filterCari);
        filterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        filterCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarRumah_user daftarRumah_user = (DaftarRumah_user) getActivity().getSupportFragmentManager().findFragmentByTag("DaftarRumah_user");

                String inputJudul = editTextJudul.getText().toString().trim();
                String Hargamin = hargamin.getText().toString().trim();
                String Hargamax = hargamax.getText().toString().trim();
                if (!Hargamin.equals("") && !Hargamax.equals("")){
                    //daftarRumah_admin.searchHarga(Hargamin,Hargamax);
                }
                if (tersedia.isChecked()){
                    daftarRumah_user.search(inputJudul, Hargamin, Hargamax, "1");
                }
                if (telahdibooking.isChecked()){
                    daftarRumah_user.search(inputJudul, Hargamin, Hargamax, "2");
                }
                if (tidaktersedia.isChecked()){
                    daftarRumah_user.search(inputJudul, Hargamin, Hargamax, "3");
                }
                if (semua.isChecked()){
                    daftarRumah_user.search(inputJudul, Hargamin, Hargamax, "");
                }
                if (!tersedia.isChecked() && !telahdibooking.isChecked() && !tidaktersedia.isChecked() && !semua.isChecked()){
                    daftarRumah_user.search(inputJudul, Hargamin, Hargamax, "");
                }

                getDialog().dismiss();
            }
        });
        return view;
    }
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
