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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.zip.Inflater;

public class FilterDialog extends DialogFragment {
    private EditText editTextJudul;
    private Button filterCari, filterCancel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog, container, false);

        editTextJudul = view.findViewById(R.id.filterJudul);
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
                String inputJudul = editTextJudul.getText().toString().trim();
                if (!inputJudul.equals("")){
                    DaftarRumah_admin daftarRumah_admin = (DaftarRumah_admin) getActivity().getSupportFragmentManager().findFragmentByTag("DaftarRumah_admin");
                    daftarRumah_admin.search(inputJudul);
                }
                getDialog().dismiss();
            }
        });

        return view;
    }
}
