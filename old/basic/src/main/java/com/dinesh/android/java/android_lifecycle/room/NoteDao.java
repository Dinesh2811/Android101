package com.dinesh.android.java.android_lifecycle.room;//package com.dinesh.android.java.android_lifecycle.room;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import java.util.List;
//
//@Dao
//public interface NoteDao {
//
//    @Insert
//    void insert_Dao(Note note);
//
//    @Query("SELECT * FROM notes")
//    LiveData<List<Note>> getAllNotes_Dao();
//
//    @Query("SELECT * FROM notes WHERE id=:noteId")
//    LiveData<Note> getNote_Dao(String noteId);
//
//    @Update
//    void update_Dao(Note note);
//
//    @Delete
//    int delete_Dao(Note note);
//}
