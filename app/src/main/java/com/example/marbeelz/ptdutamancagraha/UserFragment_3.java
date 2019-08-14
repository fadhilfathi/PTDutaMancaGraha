package com.example.marbeelz.ptdutamancagraha;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class UserFragment_3 extends Fragment {
    @Nullable
    private ProgressBar mProgressBar;
    private Context mContext;

    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private StorageReference mStorageRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Daftar Rumah");
        final View view = inflater.inflate(R.layout.user_fragment_3, container, false);

        return view;
    }
}