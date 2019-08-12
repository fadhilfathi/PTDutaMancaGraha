package com.example.marbeelz.ptdutamancagraha;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Context;
import android.content.ContentResolver;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

import static android.app.Activity.RESULT_OK;

public class Fragment_2 extends Fragment {
    @Nullable
    public static final int PICK_IMAGE_REQUEST = 1;
    EditText textNama, textAlamat, textLuasTanah, textLuasBangunan, textSumberAir;
    Button upload;
    ImageButton choose;
    ImageView imageView;
    Spinner spinnerListrik, spinnerKamarTidur, spinnerKamarMandi;
    String garasss = "asd";
    SwitchCompat garasi, carport;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    Typeface font;
    SwitchCompat switchCompat;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        getActivity().setTitle("Tambah Rumah");

        String [] listrik =
                {"Listrik","900","1300","2200"};

        String [] kamarTidur =
                {"Kamar Tidur","1","2","3","4","5"};

        String [] kamarMandi =
                {"Kamar Mandi","1","2","3"};

        spinnerListrik = view.findViewById(R.id.spinner_listrik);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listrik);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerListrik.setAdapter(adapter);

        spinnerKamarTidur = (Spinner) view.findViewById(R.id.spinner_KamarTidur);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, kamarTidur);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerKamarTidur.setAdapter(adapter2);

        spinnerKamarMandi = (Spinner) view.findViewById(R.id.spinner_KamarMandi);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, kamarMandi);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerKamarMandi.setAdapter(adapter3);


        garasi = (SwitchCompat) view.findViewById(R.id.switch_garasi);
        carport = (SwitchCompat) view.findViewById(R.id.switch_carport);

        String textGarasi = "Tidak ada";
        String textCarport = "Tidak ada";

        if (garasi.isChecked()){
            textGarasi = "Ada";
        }
        if (carport.isChecked()){
            textCarport = "Tidak Ada";
        }

        textNama = view.findViewById(R.id.edit_text2);
        textAlamat = view.findViewById(R.id.alamat);
        textLuasTanah = view.findViewById(R.id.luas_tanah);
        textLuasBangunan = view.findViewById(R.id.luas_bangunan);
        textSumberAir = view.findViewById(R.id.air);
        choose = view.findViewById(R.id.image2);
        upload = view.findViewById(R.id.upload);
        imageView = view.findViewById(R.id.image2);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textNama.getText().toString().trim().equals("")){
                    textNama.setError("Silahkan Masukkan Tipe Rumah");
                }
                if (textAlamat.getText().toString().trim().equals("")){
                    textAlamat.setError("Silahkan Masukkan Alamat");
                }
                if (textLuasTanah.getText().toString().trim().equals("")){
                    textLuasTanah.setError("Silahkan Masukkan Alamat");
                }
                if (textLuasBangunan.getText().toString().trim().equals("")){
                    textLuasBangunan.setError("Silahkan Masukkan Alamat");
                }
                if (textSumberAir.getText().toString().trim().equals("")){
                    textSumberAir.setError("Silahkan Masukkan Alamat");
                }
                if (spinnerListrik.getSelectedItem().toString().trim() == "Listrik"){
                    TextView errorListrik = (TextView)spinnerListrik.getSelectedView();
                    errorListrik.setError("");
                    errorListrik.setTextColor(Color.RED);
                }
                if (spinnerKamarTidur.getSelectedItem().toString().trim() == "Kamar Tidur"){
                    TextView errorKamarTidur = (TextView)spinnerKamarTidur.getSelectedView();
                    errorKamarTidur.setError("");
                    errorKamarTidur.setTextColor(Color.RED);
                }
                if (spinnerKamarMandi.getSelectedItem().toString().trim() == "Kamar Mandi"){
                    TextView errorKamarMandi = (TextView)spinnerKamarMandi.getSelectedView();
                    errorKamarMandi.setError("");
                    errorKamarMandi.setTextColor(Color.RED);
                }

                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(getActivity(),"Sedang Dalam Proses Upload",Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        return view;
    }

    private String getFileExtention(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtention(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Upload upload;
                            String textGarasi = "Tidak Ada";
                            String textCarport = "Tidak Ada";
                            if (garasi.isChecked()){
                                textGarasi = "Ada";
                            }
                            if (carport.isChecked()){
                                textCarport = "Ada";
                            }
                            upload = new Upload(textNama.getText().toString().trim(),
                                    textAlamat.getText().toString().trim(),
                                    textLuasTanah.getText().toString().trim(),
                                    textLuasBangunan.getText().toString().trim(),
                                    textSumberAir.getText().toString().trim(),
                                    spinnerListrik.getSelectedItem().toString().trim(),
                                    spinnerKamarTidur.getSelectedItem().toString().trim(),
                                    spinnerKamarMandi.getSelectedItem().toString().trim(),
                                    textGarasi,
                                    textCarport,
                                    uri.toString());
                            String UploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(UploadId).setValue(upload);
                        }
                    });
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(getActivity(),"Upload Sukses",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress =   (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int)progress);
                }
            });
        }else{
            Toast.makeText(getActivity(),"No File Selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser(){
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
            Picasso.get().load(mImageUri).into(imageView);
        }
    }
}

