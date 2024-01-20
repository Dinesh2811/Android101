package com.dinesh.android.rv.java.nested.v0;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinesh.android.R;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.MyViewHolder> implements RvClickInterface {
    private static final String TAG = "log_ParentAdapter";

    List<ParentModelClass> parentModelClassList;
    RvClickInterface rvClickInterface;

    ChildAdapter childAdapter = new ChildAdapter();
    Context context;

    public ParentAdapter(List<ParentModelClass> parentModelClassList, RvClickInterface rvClickInterface) {
        this.parentModelClassList = parentModelClassList;
        this.rvClickInterface = rvClickInterface;
    }

    public ParentAdapter(List<ParentModelClass> parentModelClassList) {
        this.parentModelClassList = parentModelClassList;
    }

    public ParentAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_java_nested_parent_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_parent_title.setText(parentModelClassList.get(position).title);

        ChildAdapter childAdapter;
        childAdapter = new ChildAdapter(parentModelClassList.get(position).childModelClassList, position, this);
        holder.rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_child.setAdapter(childAdapter);
    }

    @Override
    public int getItemCount() {
        return parentModelClassList.size();
    }


    //    Child onOnClickListener
    @Override
    public void onItemClick(View view, int position) {
//        Log.i(TAG, "Child onOnClickListener: ChildRvPosition = " + position);

//        Log.e(TAG, "Child onOnClickListener: "+ChildAdapter.methodforpos() );
//        Log.e(TAG, "Child onOnClickListener: "+parentModelClassList.get(childAdapter.parentRvPosition).title );
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_child;
        TextView tv_parent_title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_child = itemView.findViewById(R.id.rv_child);
            tv_parent_title = itemView.findViewById(R.id.tv_parent_title);

//            Parent OnClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    rvClickInterface.onItemClick(v, getAdapterPosition());
                    Log.i(TAG, "Parent OnClickListener: parentRvPosition = " + getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), NewActivity.class);
                    intent.putExtra("Title", RvData.getRvData().getRvDataList().get(getAdapterPosition()).getTitle());
                    v.getContext().startActivity(intent);

                }
            });

        }
    }
}
