package com.example.marbeelz.ptdutamancagraha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Fragment_1 extends Fragment implements RecycleAdapter.OnItemClickListener {
    @Nullable
    private ProgressBar mProgressBar;
    private Context mContext;

    private RecyclerView mRecyclerView;
    public RecycleAdapter mAdapter;
    private androidx.appcompat.widget.SearchView searchView;
    private ValueEventListener mDBListener;
    EditText filter;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private StorageReference mStorageRef;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Daftar Rumah");
        final View view = inflater.inflate(R.layout.fragment_1, container, false);
        //setHasOptionsMenu(true);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProgressBar = view.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new RecycleAdapter(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(Fragment_1.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Clear Model biar tidak dobel
                mUploads.clear();

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapShot.getValue(Upload.class);
                    //mengambil key dari database untuk disimpan ke model upload
                    upload.setmKey(postSnapShot.getKey());
                    mUploads.add(upload);
                }
                //update recycler view dengan item baru
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
        return view;

    }

    public void search(String str){
        List<Upload> listSearch = new ArrayList<>();
        for (Upload object : mUploads){
            if (object.getmName().toLowerCase().contains(str)){
                listSearch.add(object);
            }
        }
        mAdapter = new RecycleAdapter(getActivity(), listSearch);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int Position) {

    }
//

    @Override
    public void onBooked(int Position) {
        Upload selectedItem = mUploads.get(Position);
        final String selectedKey = selectedItem.getmKey();
        mDatabaseRef.child(selectedKey).child("mStatus").setValue("3");
    }

    @Override
    public void onAvailable(int Position) {
        Upload selectedItem = mUploads.get(Position);
        final String selectedKey = selectedItem.getmKey();
        mDatabaseRef.child(selectedKey).child("mStatus").setValue("1");
    }


    @Override
    public void onDelete(int Position) {
        Upload selectedItem = mUploads.get(Position);
        final String selectedKey = selectedItem.getmKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(getActivity(), "Delete Sukses", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
