package com.example.noteapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.model.Note
import com.example.noteapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application = application) {

    private val repository: NoteRepository

    private val getAllNotes: LiveData<MutableList<Note>>

    init {
        val dao = NoteDatabase.getInstance(application).noteDao()
        repository = NoteRepository(notesDAO = dao)
        getAllNotes = repository.getAllNotes()
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note = note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note = note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note = note)
    }

    fun deleteAllNotes() =  viewModelScope.launch {
        repository.deleteAllNotes()
    }
}