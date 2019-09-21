package com.example.marbeelz.ptdutamancagraha;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleUserAdapter extends RecyclerView.Adapter<RecycleUserAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;
    //private RecycleAdapter.OnItemClickListener mListener;

    public RecycleUserAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycleuser_item,parent,false);
        final ImageViewHolder viewHolder = new ImageViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Upload uploadcurrent = mUploads.get(position);
        holder.textViewName.setText(uploadcurrent.getmName());
        holder.textViewListrik.setText(uploadcurrent.getmListrik());
        holder.textViewAir.setText(uploadcurrent.getmSumber_Air());
        holder.textViewKamarMandi.setText(uploadcurrent.getmKamarMandi());
        holder.textViewKamarTidur.setText(uploadcurrent.getmKamarTidur());
        Picasso.get().load(uploadcurrent.getmImageUrl()).fit().placeholder(R.drawable.picture).centerCrop().into(holder.imageView);
        String status = uploadcurrent.getmStatus();
        if (status.equals("1")){
            holder.tersedia.setVisibility(View.VISIBLE);
        }
        if (status.equals("2")){
            holder.booked.setVisibility(View.VISIBLE);
        }
        if (status.equals("3")){
            holder.tidaktersedia.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailUserFragment detail = new DetailUserFragment();
                Bundle bundle = new Bundle();
                bundle.putString("key",uploadcurrent.getmKey());
                detail.setArguments(bundle);
                UserActivity userActivity = (UserActivity) mContext;
                userActivity.switchContent(R.id.fragment_container,detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName, textViewListrik, textViewAir, textViewKamarMandi, textViewKamarTidur, booked, tersedia, tidaktersedia;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewListrik = itemView.findViewById(R.id._listrik);
            textViewAir = itemView.findViewById(R.id._air);
            textViewKamarMandi = itemView.findViewById(R.id._kamarmandi);
            textViewKamarTidur = itemView.findViewById(R.id._kamartidur);
            imageView = itemView.findViewById(R.id.image_view_rumah);
            booked = itemView.findViewById(R.id.statusRumahBooked);
            tersedia = itemView.findViewById(R.id.statusRumahTersedia);
            tidaktersedia = itemView.findViewById(R.id.statusRumahTidakTersedia);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int Position);

//        void onBooked(int Position);
//
//        void onDelete(int Position);
//
//        void onAvailable(int Position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
