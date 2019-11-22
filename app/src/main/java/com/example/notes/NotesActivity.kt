package com.example.notes

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.notes.adapter.NotesRecyclerAdapter
import kotlinx.android.synthetic.main.activity_notes.*
import timber.log.Timber

class NotesActivity : AppCompatActivity() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NotesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        Timber.plant(Timber.DebugTree())

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        setupNotesRecyclerView()

        notesViewModel.noteList.observe(this, Observer {
            notesAdapter.addNotes(it)
        })

        addNoteFab.setOnClickListener {
            NoteDialog().show(supportFragmentManager, "Note Dialog")
        }

        searchEditText.setOnEditorActionListener { searchTextView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchTextView.text.toString()
                if (query.isEmpty()) {
                    return@setOnEditorActionListener true
                }
                notesViewModel.getNotes(query)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupNotesRecyclerView() {
        notesAdapter = NotesRecyclerAdapter()
        notesRecyclerView.let {
            it.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            it.adapter = notesAdapter
        }
    }
}
