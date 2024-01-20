package com.dinesh.android.java.android_lifecycle.room;//package com.dinesh.android.java.android_lifecycle.room;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//@Database(entities = Note.class, version = 1)
//public abstract class NoteRoomDatabase extends RoomDatabase {
//    //needs to be an abstract class
//    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];
//
//    public abstract NoteDao noteDao();
//
//    private static volatile NoteRoomDatabase noteRoomDatabase;
//
//    static NoteRoomDatabase getDatabase(final Context context){
//        if (noteRoomDatabase == null){
//            synchronized (NoteRoomDatabase.class){
//               if (noteRoomDatabase == null){
//                   noteRoomDatabase = Room.databaseBuilder(context.getApplicationContext(),
//                           NoteRoomDatabase.class, "note_database").build();
//               }
//            }
//        }
//
//        return noteRoomDatabase;
//    }
//
//}
