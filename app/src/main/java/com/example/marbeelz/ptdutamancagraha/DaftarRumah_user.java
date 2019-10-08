package com.example.marbeelz.ptdutamancagraha;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;

public class DaftarRumah_user extends Fragment implements RecycleAdapter_user.OnItemClickListener {
    @Nullable
    private ProgressBar mProgressBar;
    private Context mContext;

    private RecyclerView mRecyclerView;
    private RecycleAdapter_user mAdapter;
    private Button searchView;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private StorageReference mStorageRef;
    private FloatingActionButton cobaaa;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Daftar Rumah");
        final View view = inflater.inflate(R.layout.user_fragment_1, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProgressBar = view.findViewById(R.id.progress_circle);

        swipeRefreshLayout = view.findViewById(R.id.swiperefreshrumahuser);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mUploads = new ArrayList<>();

        mAdapter = new RecycleAdapter_user(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DaftarRumah_user.this);


        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");
        searchView = view.findViewById(R.id.button_searchuser);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialogUser filterDialog = new FilterDialogUser();
                filterDialog.show(getFragmentManager(), null);
            }
        });

        load();

        return view;
    }

    public void search(String judul, String min, String max, String status){
        List<Upload> listSearch = new ArrayList<>();
        for (Upload object : mUploads){
            int Harga = new Integer((object.getmHarga()));
            int Min = 0, Max = 0;
            if (!min.equals("")){
                Min = new Integer(min);
            }
            if (!max.equals("")){
                Max = new Integer(max);
            }
            String name = object.getmName().toLowerCase();
            String stats = object.getmStatus();
            if (!judul.equals("")) {
                if (name.contains(judul)) {
                    listSearch.add(object);
                }
            }
            if (stats.equals(status)){
                listSearch.add(object);
            }
            if (status.equals("")){
                listSearch.add(object);
            }
            if (Harga>=Min && Harga<=Max){
                listSearch.add(object);
            }

        }
        mAdapter = new RecycleAdapter_user(getActivity(), listSearch);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void load() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mUploads = new ArrayList<>();

        mAdapter = new RecycleAdapter_user(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DaftarRumah_user.this);


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
    }

    @Override
    public void onItemClick(int Position) {

    }


    @Override
    public void onResume() {
        //mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //mProgressBar = view.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new RecycleAdapter_user(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DaftarRumah_user.this);


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
        super.onResume();
    }
    public void sortStatus(){
        Collections.sort(mUploads, new Comparator<Upload>() {
            @Override
            public int compare(Upload upload, Upload t1) {
                return upload.getmStatus().compareTo(t1.getmStatus());
            }
        });
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
