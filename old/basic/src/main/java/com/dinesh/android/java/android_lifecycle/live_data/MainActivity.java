package com.dinesh.android.java.android_lifecycle.live_data;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dinesh.android.R;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        Log.e("TAG_", "onCreate: ");

//        getLifecycle().addObserver(new MainActivityObserver());

        //  ViewModel
        TextView mTextView = findViewById(R.id.tvNumber);
//        MainViewModel myData = new MainViewModel();
        MainViewModel myData = new ViewModelProvider(this).get(MainViewModel.class);


        //  LiveData
        LiveData<String> myRandomNumber = myData.getNumber();
//        mTextView.setText(myRandomNumber);
        myRandomNumber.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mTextView.setText(s);
            }
        });

        Log.i(TAG, "Random Number Set");

        mTextView.setOnClickListener(v -> myData.createNumber());



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.e(TAG, "onStart: ");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e(TAG, "onPause: ");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e(TAG, "onResume: ");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.e(TAG, "onStop: ");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e(TAG, "onDestroy: ");
//    }
}
