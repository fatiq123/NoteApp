package com.example.noteapp.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.model.Note

class NoteAdaptor(
    val listener: OnClickListener,
) : ListAdapter<Note, NoteAdaptor.NoteViewHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.text_view_title) as TextView
        val textDescription: TextView =
            itemView.findViewById(R.id.text_view_description) as TextView
        val textPriority: TextView = itemView.findViewById(R.id.text_view_priority) as TextView

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {   /*no_position is simply -1 so it is obvious that when it is not minus one then the item will be selected*/
                    listener.onClick(note = getItem(adapterPosition))
                }
            }
        }
    }

    interface OnClickListener {      // this interface is to edit the specific note
        fun onClick(note: Note)
    }


    /* to delete a specific note */
    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = getItem(position)
        holder.textTitle.text = note.title
        holder.textDescription.text = note.description
        holder.textPriority.text = note.priority.toString()
    }

}