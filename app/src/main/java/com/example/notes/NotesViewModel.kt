package com.example.notes

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notes.data.NotesDao
import com.example.notes.data.NotesDatabase
import com.example.notes.model.Note
import java.util.*


class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val notesDao: NotesDao?
    var noteList: MutableLiveData<List<Note>> = MutableLiveData()

    init {
        val database = NotesDatabase.getDatabase(application)
        notesDao = database?.getNotesDao()
        getNotes()
    }

    fun getNotes(query: String = "") {
        NotesAsyncTask(notesDao, noteList).execute(query)
    }

    fun addNote(title: String, content: String) {
        val note = Note(title = title, content = content, timeStamp = Date().time)
        AsyncTask.execute {
            notesDao?.addNote(note)
            getNotes()
        }
    }

    companion object {
        class NotesAsyncTask(
            private val notesDao: NotesDao?,
            private val noteList: MutableLiveData<List<Note>>
        ) : AsyncTask<String, Int, List<Note>?>() {
            override fun doInBackground(vararg query: String): List<Note>? {
                return if (query[0].isEmpty()) {
                    notesDao?.getAllNotes()
                } else {
                    notesDao?.getNotes("%${query[0]}%")
                }
            }

            override fun onPostExecute(result: List<Note>?) {
                noteList.value = result
            }
        }
    }
}