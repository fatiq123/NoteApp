package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.activities.AddEditActivity
import com.example.noteapp.adaptors.NoteAdaptor
import com.example.noteapp.model.Note
import com.example.noteapp.utils.Constants
import com.example.noteapp.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NoteAdaptor.OnClickListener {

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdaptor: NoteAdaptor
    private lateinit var recyclerView: RecyclerView
    private lateinit var button: FloatingActionButton
    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application = application)
        )[NoteViewModel::class.java]

        button = findViewById(R.id.btn_add_note)
        recyclerView = findViewById(R.id.recycler_view)

        noteAdaptor = NoteAdaptor(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdaptor



        viewModel.getAllNotes.observe(this, Observer {
            // here we can add the data to our recyclerview
            noteAdaptor.submitList(it)
        })


        getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Constants.REQUEST_CODE) {
                    val title = it.data?.getStringExtra(Constants.EXTRA_TITLE)
                    val description = it.data?.getStringExtra(Constants.EXTRA_DESCRIPTION)
                    val priority = it.data?.getIntExtra(Constants.EXTRA_PRIORITY, -1)

                    val note =
                        Note(title = title!!, description = description!!, priority = priority!!)

                    viewModel.insert(note = note)

                } else if (it.resultCode == Constants.EDIT_REQUEST_CODE) {
                    val title = it.data?.getStringExtra(Constants.EXTRA_TITLE)
                    val description = it.data?.getStringExtra(Constants.EXTRA_DESCRIPTION)
                    val priority = it.data?.getIntExtra(Constants.EXTRA_PRIORITY, -1)
                    val id = it.data?.getIntExtra(Constants.EXTRA_ID, -1)

                    val note =
                        Note(title = title!!, description = description!!, priority = priority!!)
                    note.id = id!!


                    viewModel.update(note = note)
                }
            }

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            getResult.launch(intent)
        }


        /* for swipe animation */
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedItem = noteAdaptor.getNoteAt(viewHolder.adapterPosition)     /*so we will store the removedItem in a variable*/
                viewModel.delete(noteAdaptor.getNoteAt(viewHolder.adapterPosition))

                Snackbar.make(                      /*this Snackbar is to show the message to user to UNDO the deleted item again*/
                    this@MainActivity,
                    recyclerView,
                    "Note Deleted",
                    Snackbar.LENGTH_LONG
                ).setAction("UNDO") {
                    viewModel.insert(removedItem)
                }.show()
            }

        }).attachToRecyclerView(recyclerView)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_all_notes -> {
                viewModel.deleteAllNotes()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(note: Note) {
        val title = note.title
        val description = note.description
        val priority = note.priority
        val id = note.id

        val intent = Intent(this@MainActivity, AddEditActivity::class.java)
        intent.putExtra(Constants.EXTRA_TITLE, title)
        intent.putExtra(Constants.EXTRA_DESCRIPTION, description)
        intent.putExtra(Constants.EXTRA_PRIORITY, priority)
        intent.putExtra(Constants.EXTRA_ID, id)

        getResult.launch(intent)
    }
}