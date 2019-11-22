package com.example.notes.data

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.notes.model.Note

@Dao
interface NotesDao {

    @Insert
    fun insert(note: Note)

    @Query("Delete from notes")
    fun deleteAll()

    @Query("Select * from notes where title LIKE :query OR content LIKE :query ORDER BY timeStamp DESC")
    fun getNotes(query: String): MutableLiveData<List<Note>>

    @Query("Select * from notes ORDER BY timeStamp DESC")
    fun getAllNotes(): MutableLiveData<List<Note>>
}