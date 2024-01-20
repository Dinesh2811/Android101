package com.dinesh.android.java.android_lifecycle.live_data;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class MainViewModel extends ViewModel {
    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];
//    private String myRandomNumber;
    private MutableLiveData<String> myRandomNumber;

    public MutableLiveData<String> getNumber() {
        Log.i(TAG, "Get number");
        if (myRandomNumber == null) {
            myRandomNumber = new MutableLiveData<>();
            createNumber();
        }
        return myRandomNumber;
    }

    public void createNumber() {
        Log.i(TAG, "Create new number");
        Random random = new Random();
//        myRandomNumber = "Number: " + (random.nextInt(10 - 1) + 1);
        myRandomNumber.setValue("Number: " + (random.nextInt(10 - 1) + 1));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }
}