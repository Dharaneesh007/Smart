package com.my.smart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {
    Context context;
    ArrayList<dataUser> dataUserArrayList;
    Locale id = new Locale("en","IN");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy",id);

    public AdapterItem(Context context, ArrayList<dataUser> dataUserArrayList) {
        this.context = context;
        this.dataUserArrayList = dataUserArrayList;
    }

    @NonNull
    @Override
    public AdapterItem.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ItemViewHolder holder, int position) {
        holder.viewBind(dataUserArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataUserArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView fname,cname,dt,st,et,hr,pur;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.data_fname);
            cname = itemView.findViewById(R.id.data_class);
            dt = itemView.findViewById(R.id.data_date);
            st = itemView.findViewById(R.id.data_stime);
            et = itemView.findViewById(R.id.data_etime);
            hr = itemView.findViewById(R.id.data_hr);
            pur = itemView.findViewById(R.id.data_pur);
        }

        public void viewBind(dataUser dataUser) {
            fname.setText(dataUser.getFname());
            cname.setText(dataUser.getCname());
            dt.setText(dataUser.getTime());
            st.setText(dataUser.getStime());
            et.setText(dataUser.getEtime());
            hr.setText(dataUser.getHrs());
            pur.setText(dataUser.getPur());
        }
    }
}
