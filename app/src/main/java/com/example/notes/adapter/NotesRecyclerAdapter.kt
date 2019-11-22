package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.Note
import kotlinx.android.synthetic.main.notes_item.view.*

class NotesRecyclerAdapter : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {
    private val notesList: ArrayList<Note> = ArrayList()

    fun addNotes(notesList: List<Note>) {
        this.notesList.apply {
            clear()
            addAll(notesList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notes_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        notesList[position].apply {
            holder.itemView.apply {
                notesTitle.text = title
                notesContent.text = content
            }

        }
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}