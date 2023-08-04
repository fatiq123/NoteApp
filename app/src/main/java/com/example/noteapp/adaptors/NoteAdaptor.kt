package com.example.noteapp.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.model.Note

class NoteAdaptor(
    private val listener: OnClickListener,
) : RecyclerView.Adapter<NoteAdaptor.NoteViewHolder>() {

    private var notesList: MutableList<Note> = mutableListOf()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.text_view_title) as TextView
        val textDescription: TextView =
            itemView.findViewById(R.id.text_view_description) as TextView
        val textPriority: TextView = itemView.findViewById(R.id.text_view_priority) as TextView

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClick(note = notesList[adapterPosition])
                }
            }
        }
    }

    interface OnClickListener {      // this interface is to edit the specific note
        fun onClick(note: Note)
    }

    fun setNotes(note: MutableList<Note>) {
        notesList = note
        notifyDataSetChanged()
    }

    /* to delete a specific note */
    fun getNoteAt(position: Int): Note {
        return notesList[position]
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