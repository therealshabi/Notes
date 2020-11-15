package com.example.notes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.notes.adapter.NotesRecyclerAdapter
import kotlinx.android.synthetic.main.activity_notes.*
import timber.log.Timber


class NotesActivity : AppCompatActivity() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NotesRecyclerAdapter

    private lateinit var searchRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        Timber.plant(Timber.DebugTree())

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        setupNotesRecyclerView()

        notesViewModel.noteList.observe(this, Observer {
            notesAdapter.addNotes(it)
            if (it.isEmpty()) {
                noNotesTextView.visibility = View.VISIBLE
                notesRecyclerView.visibility = View.GONE
            } else {
                noNotesTextView.visibility = View.GONE
                notesRecyclerView.visibility = View.VISIBLE
            }
        })

        addNoteFab.setOnClickListener {
            NoteDialog().show(supportFragmentManager, "Note Dialog")
        }

        searchRunnable = Runnable {
            notesViewModel.getNotes(searchEditText.text.toString())
        }
        addSearchActions()
    }

    private fun addSearchActions() {
        searchEditText.apply {
            setOnEditorActionListener { searchTextView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchTextView.text.toString()
                    if (query.isEmpty()) {
                        return@setOnEditorActionListener true
                    }
                    notesViewModel.getNotes(query)
                    searchEditText.text.clear()
                    return@setOnEditorActionListener true
                }
                false
            }

            addTextChangedListener {
                doAfterTextChanged {
                    val query = searchEditText.text.toString()
                    val delay = when (query.length) {
                        0 -> 0L
                        1 -> 1000L
                        2, 3 -> 700L
                        4, 5 -> 500L
                        else -> 300L
                    }
                    handler.removeCallbacks(searchRunnable)
                    searchRunnable = Runnable {
                        notesViewModel.getNotes(query)
                    }
                    handler.postDelayed(searchRunnable, delay)
                }
            }
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
