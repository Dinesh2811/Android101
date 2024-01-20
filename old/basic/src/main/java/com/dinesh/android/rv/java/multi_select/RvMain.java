package com.dinesh.android.rv.java.multi_select;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class RvMain extends ToolbarMain implements RvInterface, ActionMode.Callback {
    private final String TAG = "log_" + RvMain.class.getName().split(RvMain.class.getName().split("\\.")[2] + ".")[1];

    List<RvModel> rvModelList = new ArrayList<>();
    RvAdapter rvAdapter;
    RecyclerView recyclerView;
    ActionMode actionMode;
    MenuItem selectAllMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewLayout(R.layout.rv_basic_main);
        recyclerView = findViewById(R.id.recyclerView);

        // Sample Model Data
        for (int i = 0; i < 50; i++) {
            rvModelList.add(new RvModel(R.drawable.ic_launcher_foreground, "User " + (i + 1), false));
        }

        recyclerView.setHasFixedSize(true);
        rvAdapter = new RvAdapter(rvModelList, RvMain.this, this);
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

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate the menu for the contextual action mode
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.contextual_action_mode_menu, menu);

        // Store the reference to the "Select All" menu item
        selectAllMenuItem = menu.findItem(R.id.menu_select_all);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // Update the menu items here
        int count = rvAdapter.getSelectedItemCount();
        String title = count + " items selected";
        mode.setTitle(title);

        // Set the text of the "Select All" menu item
        selectAllMenuItem.setTitle(rvAdapter.getSelectedItemCount() == rvModelList.size() ? "Unselect All" : "Select All");
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Handle menu item clicks here
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                // Delete the selected items
                List<Integer> selectedItems = rvAdapter.getSelectedItems();
                for (int i = selectedItems.size() - 1; i >= 0; i--) {
                    int position = selectedItems.get(i);
                    rvAdapter.remove(position);
                }
                mode.finish();
                return true;
            case R.id.menu_select_all:
                // Select or unselect all items
                if (rvAdapter.getSelectedItemCount() == rvModelList.size()) {
                    rvAdapter.unselectAll();
                } else {
                    rvAdapter.selectAll();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // End the action mode here
        actionMode = null;
        rvAdapter.clearSelection();
    }
}
