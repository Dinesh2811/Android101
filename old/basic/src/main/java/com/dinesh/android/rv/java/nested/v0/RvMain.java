package com.dinesh.android.rv.java.nested.v0;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinesh.android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RvMain extends AppCompatActivity implements RvClickInterface {
    private static final String TAG = "log_Rv_Main";

    RecyclerView recyclerView;
    ParentAdapter parentAdapter;
    List<ParentModelClass> parentModelClassList = new ArrayList<>();
    List<ChildModelClass> childModelClassList = new ArrayList<>();


    List<ChildModelClass> favList = new ArrayList<>();
    List<ChildModelClass> recentList = new ArrayList<>();
    List<ChildModelClass> latestList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_java_nested_main);
        recyclerView = findViewById(R.id.rv_parent);

//        for (int i = 0; i < 50; i++) {
//            latestList.add(new ChildModelClass("https://picsum.photos/" + (i + 250)));
//            recentList.add(new ChildModelClass("https://picsum.photos/" + (i + 300)));
//            favList.add(new ChildModelClass("https://picsum.photos/" + (i + 350)));
//        }
//
//        parentModelClassList.add(new ParentModelClass("Latest Movies", latestList));
//        parentModelClassList.add(new ParentModelClass("Recent Movies", recentList));
//        parentModelClassList.add(new ParentModelClass("Favorite Movies", favList));
//        parentAdapter = new ParentAdapter(parentModelClassList, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(parentAdapter);


        parentAdapter = new ParentAdapter(RvData.getRvData().getRvDataList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(parentAdapter);

    }

    //    Parent onOnClickListener (using Interface)
    @Override
    public void onItemClick(View view, int position) {
//        Log.i(TAG, "Parent OnClickListener: parentRvPosition = " + position);
    }
}



