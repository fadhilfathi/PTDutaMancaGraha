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

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {
    private Context mContext;
    private List<Upload> mUpload;
    private OnItemClickListener mListener;
    public RecycleAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUpload = uploads;
    }
    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycle_item,parent,false);
        final RecycleViewHolder viewHolder = new RecycleViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, final int position) {
        final Upload uploadcurrent = mUpload.get(position);
        holder.textViewName.setText(uploadcurrent.getmName());
        holder.textViewListrik.setText(uploadcurrent.getmListrik());
        holder.textViewAir.setText(uploadcurrent.getmSumber_Air());
        holder.textViewKamarMandi.setText(uploadcurrent.getmKamarMandi());
        holder.textViewKamarTidur.setText(uploadcurrent.getmKamarTidur());
        //Glide.with(mContext).load(uploadcurrent.getmImageUrl());
        Picasso.get().load(uploadcurrent.getmImageUrl()).fit().placeholder(R.mipmap.ic_launcher).centerCrop().into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentjump(uploadcurrent);
                DetailFragment detail = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("key",uploadcurrent.getmKey());
                detail.setArguments(bundle);
                HomeActivity homeActivity = (HomeActivity) mContext;
                MainActivity mainActivity = (MainActivity) mContext;
                homeActivity.switchContent(R.id.fragment_container,detail);
                //fragment_1.getFragmentManager().beginTransaction().replace(R.id.fragment_container,detail).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName, textViewListrik, textViewAir, textViewKamarMandi, textViewKamarTidur;
        public ImageView imageView;


        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewListrik = itemView.findViewById(R.id._listrik);
            textViewAir = itemView.findViewById(R.id._air);
            textViewKamarMandi = itemView.findViewById(R.id._kamarmandi);
            textViewKamarTidur = itemView.findViewById(R.id._kamartidur);
            imageView = itemView.findViewById(R.id.image_view_rumah);


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
            MenuItem Detail = contextMenu.add(Menu.NONE,1,1,"Edit");
            MenuItem Hapus = contextMenu.add(Menu.NONE,2,2,"Hapus");

            Detail.setOnMenuItemClickListener(this);
            Hapus.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            mListener.whatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDelete(position);
                            return true;
                        case 3:
                            mListener.onItemClick(position);
                            return true;
                    }
                }
            }return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int Position);


        void whatEverClick(int Position);

        void onDelete(int Position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
