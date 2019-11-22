package com.example.notes


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_add_note.view.*

class NoteDialog : DialogFragment() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var dialogView: View

    @SuppressLint("InflateParams")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialogView = LayoutInflater.from(context)
            .inflate(R.layout.fragment_add_note, null)
        dialog.setContentView(dialogView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        activity?.let {
            notesViewModel = ViewModelProviders.of(it).get(NotesViewModel::class.java)
        }

        dialogView.apply {
            saveNoteButton.setOnClickListener {
                notesViewModel.addNote(
                    notesTitleEditText.text.toString(),
                    notesContentEditText.text.toString()
                )
                dismiss()
            }
            discardNoteButton.setOnClickListener {
                dismiss()
            }
        }
    }
}
