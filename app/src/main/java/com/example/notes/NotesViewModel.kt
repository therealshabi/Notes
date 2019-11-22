package com.example.notes

import android.app.Application
import android.os.AsyncTask
import android.util.LruCache
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notes.data.NotesDao
import com.example.notes.data.NotesDatabase
import com.example.notes.model.Note
import java.util.*


class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val notesDao: NotesDao?
    var noteList: MutableLiveData<List<Note>> = MutableLiveData()
    private val searchCache: LruCache<String, List<Note>> = LruCache(100)

    init {
        val database = NotesDatabase.getDatabase(application)
        notesDao = database?.getNotesDao()
        getNotes()
    }

    fun getNotes(query: String = "") {
        if (query.isNotEmpty() && searchCache[query] != null) {
            noteList.postValue(searchCache[query])
            return
        }
        NotesAsyncTask(notesDao, noteList, searchCache).execute(query)
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
            private val noteList: MutableLiveData<List<Note>>,
            private val searchCache: LruCache<String, List<Note>>
        ) : AsyncTask<String, Int, Pair<String, List<Note>?>>() {

            override fun doInBackground(vararg query: String): Pair<String, List<Note>?> {
                return if (query[0].isEmpty()) {
                    Pair(query[0], notesDao?.getAllNotes())
                } else {
                    Pair(query[0], notesDao?.getNotes("%${query[0]}%"))
                }
            }

            override fun onPostExecute(result: Pair<String, List<Note>?>) {
                noteList.value = result.second
                if (result.first.isNotEmpty()) {
                    searchCache.put(result.first, result.second)
                }
            }
        }
    }
}