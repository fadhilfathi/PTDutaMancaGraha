package com.example.marbeelz.ptdutamancagraha;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
    private TextView textTittle,textAlamat,textTanah,textBangunan,textAir,textListrik,textTidur,textMandi,textGarasi,textCarport;
    private ImageView imageView;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Button editBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail, container, false);
        editBtn = view.findViewById(R.id.button_edit);
        textTittle = view.findViewById(R.id.titleDetail);
        textAlamat = view.findViewById(R.id.textViewDetailAlamat);
        textTanah = view.findViewById(R.id.textViewDetailTanah);
        textBangunan = view.findViewById(R.id.textViewDetailBangunan);
        textAir = view.findViewById(R.id.textViewDetailAir);
        textListrik = view.findViewById(R.id.textViewDetailListrik);
        textTidur = view.findViewById(R.id.textViewDetailTidur);
        textMandi = view.findViewById(R.id.textViewDetailMandi);
        textCarport = view.findViewById(R.id.textViewDetailCarport);
        textGarasi = view.findViewById(R.id.textViewDetailGarasi);
        imageView = view.findViewById(R.id.imageViewDetail);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");

        final Bundle bundle = getArguments();
        final String key = bundle.getString("key");

        mDatabaseRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("mName").exists()){
                    textTittle.setText(dataSnapshot.child("mName").getValue().toString().trim());
                }
                if (dataSnapshot.child("mAlamat").exists()){
                    textAlamat.setText(dataSnapshot.child("mAlamat").getValue().toString().trim());
                }
                if (dataSnapshot.child("mLuas_Tanah").exists()){
                    textTanah.setText(dataSnapshot.child("mLuas_Tanah").getValue().toString().trim());
                }
                if (dataSnapshot.child("mLuas_Bangunan").exists()){
                    textBangunan.setText(dataSnapshot.child("mLuas_Bangunan").getValue().toString().trim());
                }
                if (dataSnapshot.child("mSumber_Air").exists()){
                    textAir.setText(dataSnapshot.child("mSumber_Air").getValue().toString().trim());
                }
                if (dataSnapshot.child("mListrik").exists()){
                    textListrik.setText(dataSnapshot.child("mListrik").getValue().toString().trim());
                }
                if (dataSnapshot.child("mKamarTidur").exists()){
                    textTidur.setText(dataSnapshot.child("mKamarTidur").getValue().toString().trim());
                }
                if (dataSnapshot.child("mKamarMandi").exists()){
                    textMandi.setText(dataSnapshot.child("mKamarMandi").getValue().toString().trim());
                }
                if (dataSnapshot.child("mGarasi").exists()){
                    textGarasi.setText(dataSnapshot.child("mGarasi").getValue().toString().trim());
                }
                if (dataSnapshot.child("mCarport").exists()){
                    textCarport.setText(dataSnapshot.child("mCarport").getValue().toString().trim());
                }
                if (dataSnapshot.child("mImageUrl").exists()) {
                    Picasso.get().load(dataSnapshot.child("mImageUrl").getValue().toString().trim()).fit().centerCrop()
                            .into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Retrive Data Error :", databaseError.toString());
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Edit fragment_edit = new Fragment_Edit();

                Bundle key = new Bundle();
                key.putString("key", bundle.getString("key"));
                fragment_edit.setArguments(key);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment_edit).addToBackStack(null).commit();
            }
        });

        return view;
    }
}