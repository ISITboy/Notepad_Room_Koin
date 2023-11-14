package com.example.myapplication.presentation.addNoteScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.data.storage.entity.Note
import com.example.data.storage.sharedpreferences.SharedPreferencesTheme
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.presentation.mainScreen.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class AddNoteFragment() : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddNoteBinding
    private val mainViewModel: MainViewModel by inject()
    private val sharedPreferencesTheme: SharedPreferencesTheme by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        initTheme()

        binding.apply {
            imgClose.setOnClickListener {
                dismiss()
            }
            if (mainViewModel.getSelectedNote().value == null) {
                clickForSave()
            } else {
                clickForUpdate()
            }
        }
    }

    private fun clickForSave() = with(binding) {
        var noteTitle: String = ""
        var noteDesc: String = ""
        btnSave.setOnClickListener {
            noteTitle = edtTitle.text.toString()
            noteDesc = edtDesc.text.toString()
            when (validateData(noteTitle, noteDesc)) {
                true -> showMessage("Title and Description cannot be Empty!")
                false -> saveNote(Note(0, noteTitle, noteDesc))
            }
        }
    }

    private fun saveNote(noteForSave: Note) {
        mainViewModel.insertNote(Note(noteForSave.id, noteForSave.title, noteForSave.description))
        showMessage("Note saved")
        dismiss()
    }

    private fun clickForUpdate() = with(binding) {
        val note = mainViewModel.getSelectedNote().value
        edtTitle.setText(note!!.title)
        edtDesc.setText(note.description)
        btnSave.setOnClickListener {
            when (validateData(edtTitle.text.toString(), edtDesc.text.toString())) {
                true -> showMessage("Title and Description cannot be Empty!")
                false -> updateNote(
                    Note(
                        note.id,
                        edtTitle.text.toString(),
                        edtDesc.text.toString()
                    )
                )
            }
        }
    }

    private fun updateNote(noteForUpdate: Note) {
        mainViewModel.updateNote(noteForUpdate)
        showMessage("Note updated")
        dismiss()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun validateData(edtTitle: String, edtDesc: String): Boolean {
        return edtTitle.isEmpty() || edtDesc.isEmpty()
    }

    private fun initTheme() = with(binding) {
        rootContainer.setBackgroundColor(
            getResources().getColor(
                sharedPreferencesTheme.getTheme().backgroundNotepadColor
            )
        )
    }
}
