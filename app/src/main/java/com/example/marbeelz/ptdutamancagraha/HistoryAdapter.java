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
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecycleViewHolder> {
    private Context mContext;
    private List<Booking> mBooking;
    private OnItemClickListener mListener;
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
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, final int position) {
        final Booking uploadcurrent = mBooking.get(position);
        holder.textJudul.setText(uploadcurrent.getJudulRumah());
        holder.namaPembeli.setText(uploadcurrent.getNamaPembeli());
        holder.noTelepon.setText(uploadcurrent.getNoHp());
        holder.namaAgen.setText(uploadcurrent.getAgen());
//        holder.textViewKamarTidur.setText(uploadcurrent.getmKamarTidur());
//        Picasso.get().load(uploadcurrent.getmImageUrl()).fit().placeholder(R.drawable.picture).centerCrop().into(holder.imageView);
//        String status = uploadcurrent.getmStatus();
//        if (status.equals("1")){
//            holder.tersedia.setVisibility(View.VISIBLE);
//        }
//        if (status.equals("2")){
//            holder.booked.setVisibility(View.VISIBLE);
//        }
//        if (status.equals("3")){
//            holder.tidaktersedia.setVisibility(View.VISIBLE);
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DetailFragment detail = new DetailFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("key",uploadcurrent.getmKey());
//                detail.setArguments(bundle);
//                HomeActivity homeActivity = (HomeActivity) mContext;
//                homeActivity.switchContent(R.id.fragment_container,detail);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mBooking.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textJudul, namaPembeli, noTelepon, namaAgen;
        public ImageView imageView;


        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            textJudul = itemView.findViewById(R.id.judul);
            namaPembeli = itemView.findViewById(R.id.namaPembeli);
            noTelepon = itemView.findViewById(R.id.noTelepon);
            namaAgen = itemView.findViewById(R.id.namaSales);
//            textViewKamarTidur = itemView.findViewById(R.id._kamartidur);
//            imageView = itemView.findViewById(R.id.image_view_rumah);
//            booked = itemView.findViewById(R.id.statusRumahBooked);
//            tersedia = itemView.findViewById(R.id.statusRumahTersedia);
//            tidaktersedia = itemView.findViewById(R.id.statusRumahTidakTersedia);

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
            contextMenu.setHeaderTitle("Select Action");
//            MenuItem Detail = contextMenu.add(Menu.NONE,1,1,"Edit");
            MenuItem Hapus = contextMenu.add(Menu.NONE,1,1,"Hapus");
            MenuItem Booked = contextMenu.add(Menu.NONE, 2,2,"TidakTersedia");
            MenuItem Available = contextMenu.add(Menu.NONE,3,3,"Tersedia");
            //Detail.setOnMenuItemClickListener(this);
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
