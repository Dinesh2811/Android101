package com.dinesh.android.java.basic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dinesh.android.R;

public class BasicMainActivity extends AppCompatActivity {
    private final String TAG = "log_" + BasicMainActivity.class.getName().split(BasicMainActivity.class.getName().split("\\.")[2] + ".")[1];

//    AutoCompleteTextView autoCompleteTextView;
//    String[] spinnerItem = {"Spinner 1", "Spinner 2", "Spinner 3", "Spinner 4", "Spinner 5",
//            "Spinner 6", "Spinner 7", "Spinner 8", "Spinner 9", "Spinner 10"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_view);

        basicSpinner();

    }

    private void basicSpinner() {
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.filled_exposed);
        String[] spinnerItem = {"Spinner 1", "Spinner 2", "Spinner 3", "Spinner 4", "Spinner 5",
                "Spinner 6", "Spinner 7", "Spinner 8", "Spinner 9", "Spinner 10"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItem);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.basic_spinner_custom_drop_down, spinnerItem);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: position " + spinnerItem[position]);
                Toast.makeText(BasicMainActivity.this, spinnerItem[position].toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}