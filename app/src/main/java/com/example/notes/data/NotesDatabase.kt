package com.example.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase? {
            if (INSTANCE == null) {
                synchronized(NotesDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NotesDatabase::class.java,
                            "notes_database"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}