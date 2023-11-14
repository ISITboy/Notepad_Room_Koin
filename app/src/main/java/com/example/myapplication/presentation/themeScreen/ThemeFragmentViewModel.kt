package com.example.myapplication.presentation.themeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.storage.sharedpreferences.SharedPreferencesTheme
import com.example.data.storage.sharedpreferences.models.Theme

class ThemeFragmentViewModel(private val sharedPreferencesTheme: SharedPreferencesTheme) {

    private val changedTheme = MutableLiveData<Theme>()
    fun getChangedTheme(): LiveData<Theme> {
        return changedTheme
    }

    fun changeTheme(theme:Theme){
        sharedPreferencesTheme.saveTheme(theme)
        changedTheme.value = theme
    }
    fun saveCurrentButton(buttonId:Int){
        sharedPreferencesTheme.saveCurrentButton(buttonId)
    }

    fun getCurrentButton() = sharedPreferencesTheme.getCurrentButton()
    fun getCurrentTheme() = sharedPreferencesTheme.getTheme()
}