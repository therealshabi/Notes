package com.example.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notes.adapter.NotesRecyclerAdapter
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NotesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        setupNotesRecyclerView()
    }

    private fun setupNotesRecyclerView() {
        notesAdapter = NotesRecyclerAdapter()
        notesRecyclerView.let {
            it.layoutManager = GridLayoutManager(this, 2)
            it.adapter = notesAdapter
        }
    }
}
