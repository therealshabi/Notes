package com.example.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notes.data.NotesDao
import com.example.notes.data.NotesDatabase
import com.example.notes.model.Note
import java.util.*

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val notesDao: NotesDao?
    var noteList: MutableLiveData<List<Note>>?

    init {
        val database = NotesDatabase.getDatabase(application)
        notesDao = database?.getNotesDao()
        noteList = notesDao?.getAllNotes()
    }

    fun addNote(title: String, content: String) {
        val note = Note(title = title, content = content, timeStamp = Date().time)
        notesDao?.addNote(note)
        noteList = notesDao?.getAllNotes()
    }

    fun searchNote(query: String) {
        notesDao?.getNotes(query)
        noteList = notesDao?.getAllNotes()
    }
}