package com.dinesh.android.java.android_lifecycle.room;//package com.dinesh.android.java.android_lifecycle.room;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.dinesh.android.R;
//
//
//public class NewNoteActivity extends AppCompatActivity {
//    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];
//
//    EditText etNewNote;
//    Button button;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_room_new);
//
//        etNewNote = findViewById(R.id.etNewNote);
//        button = findViewById(R.id.bAdd);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                if (!TextUtils.isEmpty(etNewNote.getText())){
//                    intent.putExtra("msg", etNewNote.getText().toString());
//                    setResult(1001,intent);
////                    intent.putExtra("msg", "RESULT_CANCELLED");
////                    setResult(1000,intent);
//                }
////                else {
//////                    intent.putExtra("msg", "RESULT_OK");
////                    intent.putExtra("msg", etNewNote.getText().toString());
////                    setResult(1001,intent);
////                }
//                finish();
//            }
//        });
//
//    }
//}
