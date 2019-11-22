package com.example.notes.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.notes.model.Note

class NotesDiffCallback(
    private val oldNotesList: ArrayList<Note>,
    private val newNotesList: ArrayList<Note>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotesList[oldItemPosition] == newNotesList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldNotesList.size
    }

    override fun getNewListSize(): Int {
        return newNotesList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotesList[oldItemPosition].id == newNotesList[newItemPosition].id
    }
}