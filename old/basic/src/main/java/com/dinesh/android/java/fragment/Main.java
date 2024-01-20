package com.dinesh.android.java.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.dinesh.android.R;

public class Main extends AppCompatActivity {
    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_java);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        FirstFragment fragmentOne = new FirstFragment();
        fragmentTransaction.replace(R.id.fragment,fragmentOne);
        fragmentTransaction.commit();

    }
}
