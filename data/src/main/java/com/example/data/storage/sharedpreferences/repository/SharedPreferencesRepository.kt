package com.example.data.storage.sharedpreferences.repository

import com.example.data.storage.sharedpreferences.models.Theme

interface SharedPreferencesRepository {
    fun saveTheme(theme: Theme)
    fun getTheme():Theme
    fun saveCurrentButton(id:Int)
    fun getCurrentButton():Int

}