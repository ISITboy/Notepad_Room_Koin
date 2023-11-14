package com.example.myapplication.presentation.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.NotepadRepositoryImpl
import com.example.data.storage.entity.Note
import com.example.data.storage.sharedpreferences.SharedPreferencesTheme
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainViewModel(private val notepadRepository: NotepadRepositoryImpl) :ViewModel(){
    private val sharedPreferencesTheme: SharedPreferencesTheme by inject(SharedPreferencesTheme::class.java)


    private val notes = MutableLiveData<List<Note>>()
    fun getNotes(): LiveData<List<Note>> {
        return notes
    }
    val selectedNote = MutableLiveData<Note>()
    fun getSelectedNote():LiveData<Note>{
        return selectedNote
    }
    private val notesSortedByTitle = MutableLiveData<List<Note>>()
    fun getNotesSortedByTitle():LiveData<List<Note>>{
        return notesSortedByTitle
    }


    fun insertNote(note:Note) = viewModelScope.launch{
        notepadRepository.insertNote(note)
    }
    fun deleteNote(id:Int) = viewModelScope.launch{
        launch { notepadRepository.deleteNote(id) }
    }
    fun updateNote(note:Note) = viewModelScope.launch{
        notepadRepository.updateNote(note)
    }
    fun getNote(id:Int) = viewModelScope.launch{
        selectedNote.value = notepadRepository.getNote(id)
    }
    fun getAllNote() =viewModelScope.launch{
        notepadRepository.getAllNote().collect(){
            notes.postValue(it)
        }
    }
    fun deleteAllNotes()=viewModelScope.launch {
        notepadRepository.deleteAllNotes()
    }
    fun findNoteByTitle(title:String) = viewModelScope.launch{
        notepadRepository.findNoteByTitle(title).collect(){
            notesSortedByTitle.postValue(it)
        }
    }

    fun getCurrentBackgroundNotepadColor() = sharedPreferencesTheme.getTheme().backgroundNotepadColor
    fun getCurrentToolbarColor() = sharedPreferencesTheme.getTheme().toolbarColor

}