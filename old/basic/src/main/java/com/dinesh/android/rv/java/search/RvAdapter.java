package com.dinesh.android.rv.java.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinesh.android.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * com.dinesh.android.rv.java.search.RvAdapter
 */


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    private final String TAG = "log_" + RvAdapter.class.getName().split(RvAdapter.class.getName().split("\\.")[2] + ".")[1];
    List<RvModel> rvModelList = new ArrayList<>();
    RvInterface rvInterface;

    public RvAdapter() {
    }

    public RvAdapter(List<RvModel> rvModelList) {
        this.rvModelList = rvModelList;
    }

    public RvAdapter(List<RvModel> rvModelList, RvMain rvInterface) {
        this.rvModelList = rvModelList;
        this.rvInterface = rvInterface;
    }

    public void setFilteredList(List<RvModel> rvModelList) {
        this.rvModelList = rvModelList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_basic_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.iv_profilePic.setImageResource(rvModelList.get(position).profilePic);
        holder.tv_name.setText(rvModelList.get(position).name);
        holder.tv_position.setText(String.valueOf(position));

        Glide.with(holder.itemView.getContext())
                .load("https://loremflickr.com/20" + position + "/20" + position + "/dog")
                .placeholder(rvModelList.get(position).profilePic)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(holder.iv_profilePic);

        holder.checkbox.setChecked(rvModelList.get(position).isChecked);
    }

    @Override
    public int getItemCount() {
        return rvModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profilePic;
        TextView tv_name, tv_position;
        MaterialCheckBox checkbox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profilePic = itemView.findViewById(R.id.iv_profilePic);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_position = itemView.findViewById(R.id.tv_position);
            checkbox = itemView.findViewById(R.id.checkbox);

            checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    RvModel rvModel = rvModelList.get(position);
                    rvModel.isChecked = isChecked;
                }
            });

            itemView.setOnClickListener(v -> {
                Log.d(TAG, "MyViewHolder: getBindingAdapterPosition -> " + getAbsoluteAdapterPosition());
                rvInterface.onItemClick(v, getAbsoluteAdapterPosition());
            });
        }
    }

}
