package com.example.marbeelz.ptdutamancagraha;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class Fragment_Edit extends Fragment {
    public static final int PICK_IMAGE_REQUEST = 1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Uri mImageUri;
    DatabaseReference mDatabaseRef;
    ImageButton imgButton;
    EditText editTitle, editAlamat, editTanah, editBangunan, editAir, editListrik, editTidur, editMandi, editGarasi, editCarport;
    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Edit Rumah");
        final View view = inflater.inflate(R.layout.fragment_edit, container, false);

        editTitle = view.findViewById(R.id.EditDetailTitle);
        editAlamat = view.findViewById(R.id.EditDetailAlamat);
        editTanah = view.findViewById(R.id.EditDetailTanah);
        editBangunan = view.findViewById(R.id.EditDetailBangunan);
        editAir = view.findViewById(R.id.EditDetailAir);
        editListrik = view.findViewById(R.id.EditDetailListrik);
        editTidur = view.findViewById(R.id.EditDetailTidur);
        editMandi = view.findViewById(R.id.EditDetailMandi);
        editGarasi = view.findViewById(R.id.EditDetailGarasi);
        editCarport = view.findViewById(R.id.EditDetailCarport);
        imgButton = view.findViewById(R.id.imageViewEdit);

        Bundle bundle = getArguments();
        String key = bundle.getString("key");

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");
        mDatabaseRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("mName").exists()){
                    editTitle.setText(dataSnapshot.child("mName").getValue().toString().trim());
                }
                if (dataSnapshot.child("mAlamat").exists()){
                    editAlamat.setText(dataSnapshot.child("mAlamat").getValue().toString().trim());
                }
                if (dataSnapshot.child("mLuas_Tanah").exists()){
                    editTanah.setText(dataSnapshot.child("mLuas_Tanah").getValue().toString().trim());
                }
                if (dataSnapshot.child("mLuas_Bangunan").exists()){
                    editBangunan.setText(dataSnapshot.child("mLuas_Bangunan").getValue().toString().trim());
                }
                if (dataSnapshot.child("mSumber_Air").exists()){
                    editAir.setText(dataSnapshot.child("mSumber_Air").getValue().toString().trim());
                }
                if (dataSnapshot.child("mListrik").exists()){
                    editListrik.setText(dataSnapshot.child("mListrik").getValue().toString().trim());
                }
                if (dataSnapshot.child("mKamarTidur").exists()){
                    editTidur.setText(dataSnapshot.child("mKamarTidur").getValue().toString().trim());
                }
                if (dataSnapshot.child("mKamarMandi").exists()){
                    editMandi.setText(dataSnapshot.child("mKamarMandi").getValue().toString().trim());
                }
                if (dataSnapshot.child("mGarasi").exists()){
                    editGarasi.setText(dataSnapshot.child("mGarasi").getValue().toString().trim());
                }
                if (dataSnapshot.child("mCarport").exists()){
                    editCarport.setText(dataSnapshot.child("mCarport").getValue().toString().trim());
                }
                if (dataSnapshot.child("mImageUrl").exists()) {
                    Picasso.get().load(dataSnapshot.child("mImageUrl").getValue().toString().trim()).fit().centerCrop()
                            .into(imgButton);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });



        return view;
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("Image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            //Glide.with(this).load(mImageUri).into(imageView);
            Picasso.get().load(mImageUri).into(imgButton);
        }
    }
}

