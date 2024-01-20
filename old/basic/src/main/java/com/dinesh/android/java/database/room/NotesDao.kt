package com.dinesh.android.java.database.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM table_name ORDER BY id ASC")
    fun getAllNotes(): Flow<List<Note>>
//    fun getAllNotes(): LiveData<List<Note>>


    @Query("SELECT * FROM table_name WHERE id = :id")
     fun getNoteById(id: Int): Flow<Note>
//     fun getNoteById(id: Long): LiveData<Note>

}

/*

@Dao
interface NotesDao {

    @Transaction
    @Insert
    fun insert(note: Note)

    @Transaction
//    @Query("UPDATE table_name SET notes = :note.title WHERE title = :note.title")
    @Update
    fun update(note: Note)

    @Transaction
    @Delete
    fun delete(note: Note)

    @Transaction
    @Query("SELECT * FROM table_name")
    fun getAllNotes(): LiveData<List<Note>>

    @Transaction
    @Query("SELECT * FROM table_name WHERE id = :noteId")
    fun getNoteById(noteId: Long): LiveData<Note>

    @Transaction
    @Query("SELECT COUNT(*) FROM table_name")
    fun getSize(): LiveData<Int>

}

 */