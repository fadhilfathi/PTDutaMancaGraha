package com.example.marbeelz.ptdutamancagraha;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DaftarRumah_admin extends Fragment implements RecycleAdapter_admin.OnItemClickListener {
    @Nullable
    private static final String TAG = "DaftarRumah_admin";
    private ProgressBar mProgressBar;
    private Context mContext;
    TextView available, booked, disabled;
    private RecyclerView mRecyclerView;
    public RecycleAdapter_admin mAdapter;
    private Button searchView;
    private ValueEventListener mDBListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private StorageReference mStorageRef;
    private FloatingActionButton filter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Daftar Rumah");
        final View view = inflater.inflate(R.layout.fragment_1, container, false);
        //setHasOptionsMenu(true);
        available = view.findViewById(R.id.statusRumahTersedia);
        booked = view.findViewById(R.id.statusRumahBooked);
        disabled = view.findViewById(R.id.statusRumahTidakTersedia);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mProgressBar = view.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new RecycleAdapter_admin(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DaftarRumah_admin.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");
        searchView = view.findViewById(R.id.button_search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialog filterDialog = new FilterDialog();
                filterDialog.show(getFragmentManager(), null);
            }
        });

        load();

        return view;

    }

    public void load() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mUploads = new ArrayList<>();

        mAdapter = new RecycleAdapter_admin(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DaftarRumah_admin.this);


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
        mAdapter = new RecycleAdapter_admin(getActivity(), listSearch);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int Position) {

    }

    @Override
    public void onResume() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mUploads = new ArrayList<>();

        mAdapter = new RecycleAdapter_admin(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DaftarRumah_admin.this);


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
    
    @Override
    public void onBooked(final int Position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Ubah rumah menjadi tidak tersedia?").setCancelable(true)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Upload selectedItem = mUploads.get(Position);

                        final String selectedKey = selectedItem.getmKey();
                        mDatabaseRef.child(selectedKey).child("mStatus").setValue("3");
                        mAdapter.notifyDataSetChanged();
                        refresh();
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
    private void refresh(){
        DaftarRumah_admin daftarRumahadmin = new DaftarRumah_admin();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, daftarRumahadmin).commit();
    }
    @Override
    public void onAvailable(final int Position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Ubah rumah menjadi tersedia?").setCancelable(true)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Upload selectedItem = mUploads.get(Position);
                        mAdapter.notifyDataSetChanged();
                        final String selectedKey = selectedItem.getmKey();
                        mDatabaseRef.child(selectedKey).child("mStatus").setValue("1");
                        mAdapter.notifyDataSetChanged();
                        refresh();
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


    @Override
    public void onDelete(int Position) {
        Upload selectedItem = mUploads.get(Position);
        final String selectedKey = selectedItem.getmKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(getActivity(), "Rumah dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
