package com.dinesh.android.rv.java.search;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dinesh.android.R;
import com.dinesh.android.app.ToolbarMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RvMain extends ToolbarMain implements RvInterface {
    private final String TAG = "log_" + RvMain.class.getName().split(RvMain.class.getName().split("\\.")[2] + ".")[1];

    List<RvModel> rvModelList = new ArrayList<>();
    RvAdapter rvAdapter;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewLayout(R.layout.rv_basic_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        addDataToList();

        recyclerView.setHasFixedSize(true);
        rvAdapter = new RvAdapter(rvModelList, RvMain.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(rvAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String query) {
        if (query != null) {
            ArrayList<RvModel> filteredList = new ArrayList<>();
            for (RvModel i : rvModelList) {
                if (i.name.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
                        || String.valueOf(i.profilePic).toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                    filteredList.add(i);
                }
            }

            if (filteredList.isEmpty()) {
                Log.e(TAG, "filterList: No Data found");
                rvAdapter.setFilteredList(new ArrayList<>());
            } else {
                rvAdapter.setFilteredList(filteredList);
            }
        }
    }

    private void addDataToList() {
        // Sample Model Data
        for (int i = 0; i <= 50; i++) {
            rvModelList.add(new RvModel(R.drawable.ic_launcher_foreground, "User " + (i + 1), false, false));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG, "onItemClick: " + position);
    }
}
