package com.example.marbeelz.ptdutamancagraha;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecycleViewHolder> {
    private Context mContext;
    private List<Booking> mBooking;
    private OnItemClickListener mListener;
    private DatabaseReference mDatabaseRef;
    public HistoryAdapter(Context context, List<Booking> bookings){
        mContext = context;
        mBooking = bookings;
    }
    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.status,parent,false);
        final RecycleViewHolder viewHolder = new RecycleViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder holder, final int position) {
        final Booking uploadcurrent = mBooking.get(position);
        holder.textJudul.setText(uploadcurrent.getJudulRumah());
        holder.namaPembeli.setText(uploadcurrent.getNamaPembeli());
        holder.noTelepon.setText(uploadcurrent.getNoHp());
        holder.namaAgen.setText(uploadcurrent.getAgen());
        Picasso.get().load(uploadcurrent.getmImageUrl()).fit().placeholder(R.drawable.picture).centerCrop().into(holder.imageView);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("upload");
        mDatabaseRef.child(uploadcurrent.getKeyRumah()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("mStatus").getValue().toString().trim().equals("3")){
                    holder.available.setVisibility(View.VISIBLE);
                }
                if (dataSnapshot.child("mStatus").getValue().toString().trim().equals("2")){
                    holder.warning.setVisibility(View.VISIBLE);
                }
                if (dataSnapshot.child("mStatus").getValue().toString().trim().equals("1")){
                    holder.notavailable.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (mDatabaseRef.child(uploadcurrent.getKeyRumah()).child("mStatus").toString().trim().equals("1")){
            holder.available.setVisibility(View.VISIBLE);
        }
        if (mDatabaseRef.child(uploadcurrent.getKeyRumah()).child("mStatus").toString().trim().equals("2")){
            holder.warning.setVisibility(View.VISIBLE);
        }
        if (mDatabaseRef.child(uploadcurrent.getKeyRumah()).child("mStatus").toString().trim().equals("3")){
            holder.notavailable.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mBooking.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textJudul, namaPembeli, noTelepon, namaAgen;
        public ImageView imageView,available,warning,notavailable;


        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            available = itemView.findViewById(R.id.available);
            warning = itemView.findViewById(R.id.warning);
            notavailable = itemView.findViewById(R.id.notavailable);
            textJudul = itemView.findViewById(R.id.judul);
            namaPembeli = itemView.findViewById(R.id.namaPembeli);
            noTelepon = itemView.findViewById(R.id.noTelepon);
            namaAgen = itemView.findViewById(R.id.namaSales);
            imageView = itemView.findViewById(R.id.imageViewStatus);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Ubah status booking rumah");
            MenuItem Hapus = contextMenu.add(Menu.NONE,1,1,"Hapus booking rumah");
            MenuItem Booked = contextMenu.add(Menu.NONE, 2,2,"Telah dibayar");
            MenuItem Available = contextMenu.add(Menu.NONE,3,3,"Batal");
            Hapus.setOnMenuItemClickListener(this);
            Booked.setOnMenuItemClickListener(this);
            Available.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.onDelete(position);
                            return true;
                        case 2:
                            mListener.onBooked(position);
                            return true;
                        case 3:
                            mListener.onAvailable(position);
                            return true;
                    }
                }
            }return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int Position);

        void onBooked(int Position);

        void onDelete(int Position);

        void onAvailable(int Position);
    }

    public void setOnItemClickListener(HistoryAdapter.OnItemClickListener listener){
        mListener = listener;
    }
}
