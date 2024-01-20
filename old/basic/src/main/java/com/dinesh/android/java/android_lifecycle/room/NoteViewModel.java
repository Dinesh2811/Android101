package com.dinesh.android.java.android_lifecycle.room;//package com.dinesh.android.java.android_lifecycle.room;
//
//import android.app.Application;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import java.util.List;
//
////public class NoteViewModel extends ViewModel {
//public class NoteViewModel extends AndroidViewModel {
//    private final String TAG = "log_" + getClass().getName().split(getClass().getName().split("\\.")[2] + ".")[1];
//    private NoteDao noteDao;
//    private NoteRoomDatabase noteDB;
//    private LiveData<List<Note>> mAllNotes;
//
//    public NoteViewModel(@NonNull Application application) {
//        super(application);
//
//        noteDB = NoteRoomDatabase.getDatabase(application);
//        noteDao = noteDB.noteDao();
//        mAllNotes = noteDao.getAllNotes_Dao();
//    }
//
//
//    public void insert(Note note) {
//        noteDao.insert_Dao(note);
//    }
//
//
//    public LiveData<List<Note>> getAllNotes() {
//        return mAllNotes;
//    }
//
//    public LiveData<Note> getNote(String noteId) {
//        return noteDao.getNote_Dao(noteId);
//    }
//
//    public void update(Note note) {
//        noteDao.update_Dao(note);
//    }
//
//    public void delete(Note note) {
//        noteDao.delete_Dao(note);
//    }
//
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        Log.i(TAG, "ViewModel Destroyed");
//    }
//}