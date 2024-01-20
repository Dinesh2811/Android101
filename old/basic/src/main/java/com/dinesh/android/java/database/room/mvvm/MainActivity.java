package com.dinesh.android.java.database.room.mvvm;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "log_" + MainActivity.class.getName().split(MainActivity.class.getName().split("\\.")[2] + ".")[1];
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i(TAG, "onChanged: " + users);
            }
        });

        userViewModel.insert(new User("userName 1", "password123"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userViewModel.shutdownExecutor();
    }
}