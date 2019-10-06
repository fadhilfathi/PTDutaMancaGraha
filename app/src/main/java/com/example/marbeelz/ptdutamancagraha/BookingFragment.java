package com.example.marbeelz.ptdutamancagraha;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class BookingFragment extends Fragment {
    @Nullable
    private ProgressBar mProgressBar;
    private Context mContext;
    private int x;
    private RecyclerView mRecyclerView;
    private RecycleAdapter_admin mAdapter;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef,mDatabaseRefUpload;
    private List<Upload> mUploads;
    private StorageReference mStorageRef;
    String key, UploadId, judul;
    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int CAMERA_REQUEST_CODE = 1;
    private Uri mImageUri;
    private Button Booking, disabled;
    private ImageButton KTP;
    private EditText NamaPembeli, NoHp;
    private ProgressBar progressBar;
    String currentlogin;
    private StorageTask mUploadTask;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bookinglayout, container, false);
        getActivity().setTitle("Booking Rumah");
        progressBar = view.findViewById(R.id.progress_circlebooking);
        KTP = view.findViewById(R.id.imageKTP);
        Booking = view.findViewById(R.id.button_ktp);
        NamaPembeli = view.findViewById(R.id.namaPembeli);
        NoHp = view.findViewById(R.id.nomorHP);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Booking");
        mDatabaseRefUpload = FirebaseDatabase.getInstance().getReference("upload");
        Bundle b = this.getArguments();
        key = b.getString("Key");
        judul = b.getString("Judul");
        KTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", file);
                m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(m_intent, CAMERA_REQUEST_CODE);
            }
        });
        mDatabaseRefUpload.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("mStatus").getValue().toString().trim();
                if (status.equals("2") || status.equals("3")){
                    Booking.setVisibility(View.INVISIBLE);
                    Booking.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("currentlogin",0);;
        currentlogin = mPrefs.getString("logincurrent","");
        return view;
    }

    private String getFileExtention(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            //TODO... onCamera Picker Result
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    //File object of camera image
                    File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");

                    //Uri of camera image
                    mImageUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", file);
                    Picasso.get().load(mImageUri).into(KTP);
                }
                break;
        }
    }

    private void uploadFile() {
        if (mImageUri != null){
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtention(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Booking booking;
                            booking = new Booking(key,judul,
                                    NamaPembeli.getText().toString().trim(),
                                    NoHp.getText().toString().trim(),
                                    currentlogin,"2",
                                    uri.toString()
                            );
                            UploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(UploadId).setValue(booking);
                            mDatabaseRefUpload.child(key).child("mStatus").setValue("2");
                        }
                    });
                    Toast.makeText(getActivity(),"Silahkan Melakukan Proses Pembayaran",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("Nama",NamaPembeli.getText().toString().trim());
                    bundle.putString("NoHp",NoHp.getText().toString().trim());
                    AfterBooking afterBooking = new AfterBooking();
                    afterBooking.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container,afterBooking).addToBackStack(null).commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getActivity(),"No File Selected",Toast.LENGTH_SHORT).show();
        }
    }

}