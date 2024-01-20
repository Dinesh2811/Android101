package com.dinesh.android.rv.java.multi_select;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
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
 * RvAdapter
 */


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];

    List<RvModel> rvModelList = new ArrayList<>();
    RvInterface rvInterface;
    RvMain rvMain;

    ActionMode actionMode;
    SparseBooleanArray selectedItems = new SparseBooleanArray();

    public RvAdapter(List<RvModel> rvModelList, RvInterface rvInterface, RvMain rvMain) {
        this.rvModelList = rvModelList;
        this.rvInterface = rvInterface;
        this.rvMain = rvMain;
    }

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
//        holder.checkbox.setChecked(rvModelList.get(position).isChecked);
        holder.checkbox.setChecked(getSelectedItems().contains(position));
        holder.checkbox.setVisibility(getSelectedItems().contains(position) ? View.VISIBLE : View.GONE);


        Glide.with(holder.itemView.getContext())
//                .load("https://wallpapers.com/images/high/minimalist-green-android-logo-xmvp2a4zfaq70rlt.jpg")
                .load("https://loremflickr.com/20" + position + "/20" + position + "/dog")
                .placeholder(rvModelList.get(position).profilePic)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(holder.iv_profilePic);
    }

    @Override
    public int getItemCount() {
        return rvModelList.size();
    }

    public void remove(int position) {
        rvModelList.remove(position);
        notifyItemRemoved(position);
    }

    public void clearSelection() {
        selectedItems.clear();
        actionMode = null;
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void selectAll() {
        for (int i = 0; i < rvModelList.size(); i++) {
            selectedItems.put(i, true);
        }
        actionMode.invalidate();
        notifyDataSetChanged();
    }

    public void unselectAll() {
        selectedItems.clear();
        actionMode.invalidate();
        notifyDataSetChanged();
    }

    public boolean areAllItemsSelected() {
        return getSelectedItemCount() == rvModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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

            iv_profilePic.setOnClickListener(this);
            tv_name.setOnClickListener(this);
            tv_position.setOnClickListener(this);
            checkbox.setOnClickListener(this);
            itemView.setOnClickListener(this);

            iv_profilePic.setOnLongClickListener(this);
            tv_name.setOnLongClickListener(this);
            tv_position.setOnLongClickListener(this);
            checkbox.setOnLongClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "MyViewHolder: getBindingAdapterPosition -> " + getAbsoluteAdapterPosition());
            rvInterface.onItemClick(view, getAbsoluteAdapterPosition());
            if (actionMode != null) {
                toggleSelection(getAbsoluteAdapterPosition());
            } else {
                rvInterface.onItemClick(view, getAbsoluteAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (actionMode == null) {
                actionMode = rvMain.startActionMode(rvMain);
            }
            toggleSelection(getAbsoluteAdapterPosition());
            return true;
        }
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
        int count = getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(count + " items selected");
            actionMode.invalidate();
        }
    }

}
