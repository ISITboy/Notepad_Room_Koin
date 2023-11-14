package com.example.myapplication.presentation.themeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.data.storage.sharedpreferences.models.Theme
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentThemeBinding
import com.example.myapplication.presentation.themeScreen.ThemeFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class ThemeFragment : BottomSheetDialogFragment() {

    private lateinit var binding :FragmentThemeBinding
    private val tfvm : ThemeFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThemeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        binding.close.setOnClickListener {
            dismiss()
        }
        initCurrentButton()
        initTheme()
        selectColor()

    }

    private fun selectColor()=with(binding){
        currentPick.setOnCheckedChangeListener { _, i ->
            when(i) {
                blue.id -> {
                    tfvm.changeTheme(Theme(
                        toolbarColor = R.color.background_toolbar_blue,
                        backgroundNotepadColor = R.color.background_notepad_blue,
                        backgroundNoteColor = R.color.background_note_blue
                    ))
                    rootComponent.setBackgroundColor(getResources().getColor(R.color.background_notepad_blue))
                    tfvm.saveCurrentButton(currentPick.checkedRadioButtonId)
                }

                yellow.id -> {
                    tfvm.changeTheme(Theme(
                        toolbarColor = R.color.background_toolbar_yellow,
                        backgroundNotepadColor = R.color.background_notepad_yellow,
                        backgroundNoteColor = R.color.background_note_yellow
                    ))
                    rootComponent.setBackgroundColor(getResources().getColor(R.color.background_notepad_yellow))
                    tfvm.saveCurrentButton(currentPick.checkedRadioButtonId)
                }

                green.id -> {
                    tfvm.changeTheme(Theme(
                        toolbarColor = R.color.background_toolbar_green,
                        backgroundNotepadColor = R.color.background_notepad_green,
                        backgroundNoteColor = R.color.background_note_green
                    ))
                    rootComponent.setBackgroundColor(getResources().getColor(R.color.background_notepad_green))
                    tfvm.saveCurrentButton(currentPick.checkedRadioButtonId)
                }

                gray.id -> {
                    tfvm.changeTheme(
                        Theme(
                            toolbarColor = R.color.background_toolbar_gray,
                            backgroundNotepadColor = R.color.background_notepad_gray,
                            backgroundNoteColor = R.color.background_note_gray
                        )
                    )
                    rootComponent.setBackgroundColor(getResources().getColor(R.color.background_notepad_gray))
                    tfvm.saveCurrentButton(currentPick.checkedRadioButtonId)
                }

                pink.id -> {
                    tfvm.changeTheme(
                        Theme(
                            toolbarColor = R.color.background_toolbar_pink,
                            backgroundNotepadColor = R.color.background_notepad_pink,
                            backgroundNoteColor = R.color.background_note_pink
                        )
                    )
                    rootComponent.setBackgroundColor(getResources().getColor(R.color.background_notepad_pink))
                    tfvm.saveCurrentButton(currentPick.checkedRadioButtonId)                    }
            }
        }
    }

    private fun initCurrentButton() = with(binding) {
        when (tfvm.getCurrentButton()) {
            blue.id -> blue.isChecked = true
            yellow.id -> yellow.isChecked = true
            green.id -> green.isChecked = true
            gray.id -> gray.isChecked = true
            pink.id -> pink.isChecked = true
        }
    }
    private fun initTheme() = with(binding){
        rootComponent.setBackgroundColor(getResources().getColor(
            tfvm.getCurrentTheme().backgroundNotepadColor
        ))
    }
}