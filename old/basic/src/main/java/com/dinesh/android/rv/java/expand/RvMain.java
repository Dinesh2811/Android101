package com.dinesh.android.rv.java.expand;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinesh.android.R;
import com.dinesh.android.app.ToolbarMain;

import java.util.ArrayList;
import java.util.List;

public class RvMain extends ToolbarMain implements RvInterface {
    private final String TAG = "log_" + RvMain.class.getName().split(RvMain.class.getName().split("\\.")[2] + ".")[1];

    List<RvModel> rvModelList = new ArrayList<>();
    RvAdapter rvAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewLayout(R.layout.rv_basic_main);
        recyclerView = findViewById(R.id.recyclerView);

        //Sample Model Data
        for (int i = 0; i < 50; i++) {
            rvModelList.add(new RvModel(R.drawable.ic_launcher_foreground, "User " + (i + 1), false));
        }

        recyclerView.setHasFixedSize(true);
        rvAdapter = new RvAdapter(rvModelList, RvMain.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(rvAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.e(TAG, "onItemClick: position -> " + position);
//        Intent intent = new android.content.Intent(this, NewLayout.class);
//        intent.putExtra("NAME", rvModelList.get(position).name);
//        startActivity(intent);

        /* ---- NewLayout.class ----
//        String movieName = getIntent().getStringExtra("NAME");
         */

    }
}
