package com.dinesh.android.rv.java.expand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinesh.android.R;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * RvAdapter
 */


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    private final String TAG = "log_" + RvAdapter.class.getName().split(RvAdapter.class.getName().split("\\.")[2] + ".")[1];

    List<RvModel> rvModelList = new ArrayList<>();
    RvInterface rvInterface;

    public RvAdapter(List<RvModel> rvModelList, RvInterface rvInterface) {
        this.rvModelList = rvModelList;
        this.rvInterface = rvInterface;
    }

    public RvAdapter(List<RvModel> rvModelList) {
        this.rvModelList = rvModelList;
    }

    public RvAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_basic_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iv_profilePic.setImageResource(rvModelList.get(position).profilePic);
        holder.tv_name.setText(rvModelList.get(position).name);
        holder.tv_position.setText(String.valueOf(position));
        holder.checkbox.setChecked(rvModelList.get(position).isChecked);

        Glide.with(holder.itemView.getContext())
                .load("https://loremflickr.com/20" + position + "/20" + position + "/dog")
                .placeholder(rvModelList.get(position).profilePic)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(holder.iv_profilePic);

        boolean isExpandable = rvModelList.get(position).isExpanded;
        holder.tvDesc.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    private void isAnyItemExpanded(int position) {
        int temp = -1;
        for (int i = 0; i < rvModelList.size(); i++) {
            if (rvModelList.get(i).isExpanded) {
                temp = i;
                break;
            }
        }
        if (temp >= 0 && temp != position) {
            rvModelList.get(temp).isExpanded = false;
            notifyItemChanged(temp, null);
        }
    }

    @Override
    public int getItemCount() {
        return rvModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profilePic;
        TextView tv_name, tv_position;
        MaterialCheckBox checkbox;
        MaterialTextView tvDesc;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profilePic = itemView.findViewById(R.id.iv_profilePic);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_position = itemView.findViewById(R.id.tv_position);
            checkbox = itemView.findViewById(R.id.checkbox);
            tvDesc = itemView.findViewById(R.id.langDesc);
            constraintLayout = itemView.findViewById(R.id.framelayout_main);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isAnyItemExpanded(getBindingAdapterPosition());
                    rvModelList.get(getBindingAdapterPosition()).isExpanded = !rvModelList.get(getBindingAdapterPosition()).isExpanded;
                    notifyItemChanged(getBindingAdapterPosition(), null);
                    if (rvInterface != null) {
                        rvInterface.onItemClick(v, getBindingAdapterPosition());
                    }
                }
            });

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    rvModelList.get(getBindingAdapterPosition()).isChecked = isChecked;
                }
            });
        }
    }

}
