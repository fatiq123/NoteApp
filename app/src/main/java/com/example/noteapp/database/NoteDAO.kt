package com.example.noteapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.model.Note

@Dao
interface NoteDAO {

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM NoteTable")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM NoteTable ORDER BY priority ASC")
    fun getAllNotes(): LiveData<MutableList<Note>>
}
