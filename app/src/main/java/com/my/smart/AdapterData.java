package com.my.smart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolder> {

    Context context;
    List<dataUser> data_list;

    public AdapterData(Context context, List<dataUser> data_list) {
        this.context = context;
        this.data_list = data_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (data_list != null && data_list.size()>0){
            holder.viewBind(data_list.get(position));
        }else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sno_tv, fname_tv, class_tv, date_tv, stime_tv, etime_tv, hr_tv, pur_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fname_tv = itemView.findViewById(R.id.data_fname);
            class_tv = itemView.findViewById(R.id.data_class);
            date_tv = itemView.findViewById(R.id.data_date);
            stime_tv = itemView.findViewById(R.id.data_stime);
            etime_tv = itemView.findViewById(R.id.data_etime);
            hr_tv = itemView.findViewById(R.id.data_hr);
            pur_tv = itemView.findViewById(R.id.data_pur);
        }

        public void viewBind(dataUser dataUser) {
            fname_tv.setText(dataUser.getFname());
            class_tv.setText(dataUser.getCname());
            date_tv.setText(dataUser.getTime());
            stime_tv.setText(dataUser.getStime());
            etime_tv.setText(dataUser.getEtime());
            hr_tv.setText(dataUser.getHrs());
            pur_tv.setText(dataUser.getPur());
        }
    }
}
