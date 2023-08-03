package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.activities.AddEditActivity
import com.example.noteapp.adaptors.NoteAdaptor
import com.example.noteapp.model.Note
import com.example.noteapp.utils.Constants
import com.example.noteapp.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdaptor: NoteAdaptor
    private lateinit var recyclerView: RecyclerView
    private lateinit var button: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application = application)
        )[NoteViewModel::class.java]

        button = findViewById(R.id.btn_add_note)
        recyclerView = findViewById(R.id.recycler_view)

        noteAdaptor = NoteAdaptor()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdaptor



        viewModel.getAllNotes.observe(this, Observer {
            // here we can add the data to our recyclerview
            noteAdaptor.setNotes(it)
        })


        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Constants.REQUEST_CODE) {
                    val title = it.data?.getStringExtra(Constants.EXTRA_TITLE)
                    val description = it.data?.getStringExtra(Constants.EXTRA_DESCRIPTION)
                    val priority = it.data?.getIntExtra(Constants.EXTRA_PRIORITY, -1)

                    val note =
                        Note(title = title!!, description = description!!, priority = priority!!)
                    viewModel.insert(note = note)
                }
            }

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            getResult.launch(intent)
        }


    }
}