package com.example.noteapp.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.model.Note

class NoteAdaptor() : RecyclerView.Adapter<NoteAdaptor.NoteViewHolder>() {

    private var notesList: MutableList<Note> = mutableListOf()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.text_view_title) as TextView
        val textDescription: TextView =
            itemView.findViewById(R.id.text_view_description) as TextView
        val textPriority: TextView = itemView.findViewById(R.id.text_view_priority) as TextView
    }

    fun setNotes(note: MutableList<Note>) {
        notesList = note
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = notesList[position]
        holder.textTitle.text = note.title
        holder.textDescription.text = note.description
        holder.textPriority.text = note.priority.toString()
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}