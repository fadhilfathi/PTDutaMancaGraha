package com.example.marbeelz.ptdutamancagraha;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class Fragment_Edit extends Fragment {
    public static final int PICK_IMAGE_REQUEST = 1;
    View view;
    Upload current;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private Uri mImageUri;
    DatabaseReference mDatabaseRef;
    ImageButton imgButton;
    EditText editTitle, editAlamat, editTanah, editBangunan, editAir, editListrik, editTidur, editMandi, editGarasi, editCarport;
    Button button;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Edit Rumah");
        view = inflater.inflate(R.layout.fragment_edit, container, false);

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
        button = view.findViewById(R.id.button_edit);

        final Bundle bundle = getArguments();
        final String key = bundle.getString("key");

        mStorageRef = FirebaseStorage.getInstance().getReference();
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Yakin?").setCancelable(true)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mImageUri != null) {
                                    final StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtention(mImageUri));
                                    mUploadTask = storageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Upload upload = new Upload(
                                                            editTitle.getText().toString().trim(),
                                                            editAlamat.getText().toString().trim(),
                                                            editTanah.getText().toString().trim(),
                                                            editBangunan.getText().toString().trim(),
                                                            editAir.getText().toString().trim(),
                                                            editListrik.getText().toString().trim(),
                                                            editTidur.getText().toString().trim(),
                                                            editMandi.getText().toString().trim(),
                                                            editGarasi.getText().toString().trim(),
                                                            editCarport.getText().toString().trim(),
                                                            uri.toString()
                                                    );
                                                    mDatabaseRef.child(key).setValue(upload);
                                                }
                                            });

                                            Toast.makeText(getActivity(), "Edit Sukses", Toast.LENGTH_SHORT).show();
                                            DetailFragment detailFragment = new DetailFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("key",key);
                                            detailFragment.setArguments(bundle);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            fragmentManager.beginTransaction().replace(R.id.fragment_container,detailFragment)
                                                    .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                                                            R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                                        }
                                    });
                                }else {
                                    mDatabaseRef.child(key).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            current = dataSnapshot.getValue(Upload.class);
                                            Upload upload = new Upload(
                                                    editTitle.getText().toString().trim(),
                                                    editAlamat.getText().toString().trim(),
                                                    editTanah.getText().toString().trim(),
                                                    editBangunan.getText().toString().trim(),
                                                    editAir.getText().toString().trim(),
                                                    editListrik.getText().toString().trim(),
                                                    editTidur.getText().toString().trim(),
                                                    editMandi.getText().toString().trim(),
                                                    editGarasi.getText().toString().trim(),
                                                    editCarport.getText().toString().trim(),
                                                    current.getmImageUrl().toString().trim()
                                            );
                                            mDatabaseRef.child(key).setValue(upload);
                                            Toast.makeText(getActivity(), "Edit Sukses", Toast.LENGTH_SHORT).show();
                                            DetailFragment detailFragment = new DetailFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("key",key);
                                            detailFragment.setArguments(bundle);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                                                    R.anim.enter_left_to_right,R.anim.exit_left_to_right).replace(R.id.fragment_container,detailFragment)
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                ;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imgButton);
        }
    }

    private String getFileExtention(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}

