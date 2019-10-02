package com.example.marbeelz.ptdutamancagraha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DaftarBooking_admin extends Fragment implements HistoryAdapter.OnItemClickListener {
    @Nullable
    private ProgressBar mProgressBar;
    private Context mContext;
    String currentlogin;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef, mDatabaseRefUpload;
    private List<Booking> mBooking;
    private StorageReference mStorageRef;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Daftar Booking");
        final View view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerviewhistory);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("currentlogin", 0);
        currentlogin = sharedPreferences.getString("logincurrent", "");
        mProgressBar = view.findViewById(R.id.progress_circle);

        swipeRefreshLayout = view.findViewById(R.id.swiperefreshbookingadmin);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mBooking = new ArrayList<>();

        mAdapter = new HistoryAdapter(getActivity(), mBooking);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(DaftarBooking_admin.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Booking");
        mDatabaseRefUpload = FirebaseDatabase.getInstance().getReference("upload");

        load();

        return view;
    }

    private void load() {
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Clear Model biar tidak dobel
                mBooking.clear();

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Booking booking = postSnapShot.getValue(Booking.class);
                    //mengambil key dari database untuk disimpan ke model upload
                    booking.setmKey(postSnapShot.getKey());
                    mBooking.add(booking);
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
    public void onBooked(final int Position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Apakah rumah sudah dibayar?").setCancelable(true)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Booking selectedItem = mBooking.get(Position);
                        final String selectedKey = selectedItem.getmKey();
                        mDatabaseRef.child(selectedKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String keyRumah = dataSnapshot.child("keyRumah").getValue().toString().trim();
                                mDatabaseRefUpload.child(keyRumah).child("mStatus").setValue("3");
                                mDatabaseRef.child(selectedKey).child("mStatusBooking").setValue("3");
                                mAdapter.notifyDataSetChanged();
                                refresh();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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
    public void onAvailable(final int Position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Apakah pembookingan rumah dibatalkan?").setCancelable(true)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Booking selectedItem = mBooking.get(Position);
                        final String selectedKey = selectedItem.getmKey();
                        mDatabaseRef.child(selectedKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String keyRumah = dataSnapshot.child("keyRumah").getValue().toString().trim();
                                mDatabaseRefUpload.child(keyRumah).child("mStatus").setValue("1");
                                mDatabaseRef.child(selectedKey).child("mStatusBooking").setValue("1");
                                mAdapter.notifyDataSetChanged();
                                refresh();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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
        Booking selectedItem = mBooking.get(Position);
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
    private void refresh(){
        DaftarBooking_admin fragment_1 = new DaftarBooking_admin();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment_1).commit();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
