package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.adaptors.NoteAdaptor
import com.example.noteapp.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdaptor: NoteAdaptor
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application = application)
        )[NoteViewModel::class.java]

        recyclerView = findViewById(R.id.recycler_view)

        noteAdaptor = NoteAdaptor()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdaptor



        viewModel.getAllNotes.observe(this, Observer {
            // here we can add the data to our recyclerview
            noteAdaptor.setNotes(it)
        })
    }
}