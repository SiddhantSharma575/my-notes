package com.example.mynotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.db.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter(val notes: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.item_title_tv.text = notes[position].title
        holder.itemView.view_item_note.text = notes[position].note

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)
            
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}