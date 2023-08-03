package com.example.noteapp.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.model.Note

class NoteAdaptor(
    val context: Context,
    private val notesList: List<Note>,
) : RecyclerView.Adapter<NoteAdaptor.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.text_view_title) as TextView
        private val textDescription: TextView = itemView.findViewById(R.id.text_view_description) as TextView
        private val textPriority: TextView = itemView.findViewById(R.id.text_view_priority) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdaptor.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = notesList[position]


    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}