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
import android.widget.Filter;
import android.widget.Filterable;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> implements Filterable {
    private Context mContext;
    private List<Upload> mUpload;
    private List<Upload> mUploadFull;
    private OnItemClickListener mListener;
    public RecycleAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUpload = uploads;
        mUploadFull = new ArrayList<>(uploads);
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
                DetailFragment detail = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("key",uploadcurrent.getmKey());
                detail.setArguments(bundle);
                HomeActivity homeActivity = (HomeActivity) mContext;
                homeActivity.switchContent(R.id.fragment_container,detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    void setFilter(List<Upload> filterList){
        mUpload.clear();
        mUpload.addAll(filterList);
        notifyDataSetChanged();
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Upload> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mUploadFull);
            } else {
                String  filterPattern = charSequence.toString().toLowerCase().trim();

                for (Upload item : mUploadFull){
                    if (item.getmName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            //mUpload.clear();
            mUpload.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

    };

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textViewName, textViewListrik, textViewAir, textViewKamarMandi, textViewKamarTidur, booked, tersedia, tidaktersedia;
        public ImageView imageView;


        public RecycleViewHolder(@NonNull View itemView) {
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

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
