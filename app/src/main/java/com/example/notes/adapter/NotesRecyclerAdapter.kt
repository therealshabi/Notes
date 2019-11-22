package com.example.notes.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.model.Note

class NotesRecyclerAdapter : RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder>() {
    private val notesList: ArrayList<Note> = ArrayList()

    private fun addNotes(notesList: ArrayList<Note>) {
        notesList.apply {
            clear()
            addAll(notesList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}