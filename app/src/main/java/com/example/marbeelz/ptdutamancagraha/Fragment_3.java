package com.example.marbeelz.ptdutamancagraha;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragment_3 extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Users;
    EditText username,password;
    Button button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_3, container, false);

        Users = database.getReference("Users");
        username = view.findViewById(R.id.username_regist);
        password = view.findViewById(R.id.password_regist);
        button = view.findViewById(R.id.button_regist);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Username = username.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                Users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(Username).exists()){
                            Toast userToast = Toast.makeText(getActivity(),"Username Telah Terdaftar Sebagai Sales, Silahkan Masukkan Username Lain",Toast.LENGTH_LONG);
                            TextView toast = userToast.getView().findViewById(android.R.id.message);
                            if (toast != null){
                                toast.setGravity(Gravity.CENTER);
                                userToast.show();
                            }
                            username.setText("");
                            password.setText("");
                        }else {
                            User user = new User(Username, Password);
                            Users.child(Username).setValue(user);
                            username.setText("");
                            password.setText("");
                            Toast.makeText(getActivity(),"Daftar Sales Berhasil",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

}

