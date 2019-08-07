package com.example.marbeelz.ptdutamancagraha;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_3 extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Users;
    EditText username,password;
    Button button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Tambah Akun");
        final View view = inflater.inflate(R.layout.fragment_3, container, false);

        Users = database.getReference("Users");
        username = view.findViewById(R.id.username_regist);
        password = view.findViewById(R.id.password_regist);
        button = view.findViewById(R.id.button_regist);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString().trim();
                String Password = password.getText().toString().trim();

                User user = new User(Username, Password);
                Users.child(Username).setValue(user);
                username.setText("");
                password.setText("");
                Toast.makeText(getActivity(),"Daftar Sales Berhasil",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}

