package com.dinesh.android.java.database.room

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//@RequiresApi(Build.VERSION_CODES.O)
//class NotesViewModel(application: Application) : AndroidViewModel(application) {
//    private val notesRepository: NotesRepository
//
//    val allNotes: LiveData<List<Note>>
//    val size: LiveData<Int>
//
//    init {
//        val notesDao = NotesDatabase.getInstance(application).notesDao()
//        notesRepository = NotesRepository(notesDao)
//        allNotes = notesRepository.getAllNotes()
//        size = notesRepository.getSize()
//    }
//
//    fun getNoteById(noteId: Long): LiveData<Note> {
//        return notesRepository.getNoteById(noteId)
//    }
//
//    fun insert(note: Note) {
//        notesRepository.insert(note)
//    }
//
//    fun update(note: Note) {
//        notesRepository.update(note)
//    }
//
//    fun delete(note: Note) {
//        notesRepository.delete(note)
//    }
//
//
//    fun shutdown() {
//        notesRepository.shutdown()
//    }
//}


//class NotesViewModel(application: Application) : AndroidViewModel(application) {
//    private val notesDao: NotesDao
//    private val executor = Executors.newSingleThreadExecutor()
//
//    val allNotes: LiveData<List<Note>>
//
//    init {
//        val notesDatabase = NotesDatabase.getInstance(application)
//        notesDao = notesDatabase.notesDao()
//        allNotes = notesDao.getAllNotes()
//    }
//
//    fun insert(note: Note) {
//        executor.execute { notesDao.insert(note) }
//    }
//
//    fun update(note: Note) {
//        executor.execute { notesDao.update(note) }
//    }
//
//    fun delete(note: Note) {
//        executor.execute { notesDao.delete(note) }
//    }
//
//    fun shutdown() {
//        executor.shutdown()
//    }
//}


@RequiresApi(Build.VERSION_CODES.O)
class NotesViewModel(application: Application) : AndroidViewModel(application) {

    public val noteDao: NotesDao = NotesDatabase.getInstance(application).notesDao()

    fun getNoteById(id: Int): Flow<Note> {
        return noteDao.getNoteById(id)
    }


    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }
}