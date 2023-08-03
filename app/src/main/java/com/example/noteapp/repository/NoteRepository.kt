package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.database.NoteDAO
import com.example.noteapp.model.Note

class NoteRepository(private val notesDAO: NoteDAO) {


    suspend fun insert(note: Note) {
        notesDAO.insert(note = note)
    }

    suspend fun delete(note: Note) {
        notesDAO.delete(note = note)
    }

    suspend fun update(note: Note) {
        notesDAO.update(note = note)
    }

    fun deleteAllNotes() {
        notesDAO.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<MutableList<Note>> {
        return notesDAO.getAllNotes()
    }

}