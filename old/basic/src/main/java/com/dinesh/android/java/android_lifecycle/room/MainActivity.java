package com.dinesh.android.java.android_lifecycle.room;//package com.dinesh.android.java.android_lifecycle.room;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.dinesh.sqlite.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//public class MainActivity extends AppCompatActivity {
//    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];
//    NoteViewModel noteViewModel;
//
//    FloatingActionButton fab;
//    TextView tvNumber;
//    String date;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_room_main);
//        Log.e("TAG_", "onCreate: ");
//        fab = findViewById(R.id.fab);
//        tvNumber = findViewById(R.id.tvNumber);
//        //  ViewModel
//        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
//
//
//        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
//            @Override
//            public void onChanged(@Nullable List<Note> notes) {
//                Log.e(TAG, "getAllNotes: " + notes);
//            }
//        });
//
//        // getNote
//        noteViewModel.getNote("100").observe(this, note -> {
//            Log.i(TAG, "getNote: " + note);
//        });
//
//        //
//        date = new Date().toString();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                noteViewModel.update(new Note("1001", new Date().toString()));
//                Log.i(TAG, "run: " + "updated");
//
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });
//
//            }
//        }).start();
//
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activityResultLauncher.launch(new Intent(MainActivity.this, NewNoteActivity.class));
//            }
//        });
//
//    }
//
//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        noteViewModel.delete(new Note("1001", date));
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                noteViewModel.delete(new Note("1001", date));
//                Log.i(TAG, "run: " + "delete");
//
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });
//
//            }
//        }).start();
//    }
//
//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(), result -> {
//                if (result.getResultCode() == 1001) {
//                    if (result.getData() != null) {
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Note note = new Note(UUID.randomUUID().toString(), result.getData().getStringExtra("msg").toString());
//                                noteViewModel.insert(note);
//                                Log.i(TAG, "run: " + "saved");
//
//                                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                    }
//                                });
//
//                            }
//                        }).start();
//
//
//                    }
//                } else {
//                    Log.e(TAG, "onCreate: " + "This is a null value");
//                }
//            });
//
//
//}
