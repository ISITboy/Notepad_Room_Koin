package com.example.data.storage.sharedpreferences

import android.content.Context
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.BASE_CURRENT_BUTTON
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.BASE_NOTEPAD_COLOR
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.BASE_NOTE_COLOR
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.BASE_TOOLBAR_COLOR
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.KEY_BACKGROUND_NOTEPAD_COLOR
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.KEY_BACKGROUND_NOTE_COLOR
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.KEY_CURRENT_BUTTON
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.KEY_TOOLBAR_COLOR
import com.example.data.storage.sharedpreferences.ConstanceSharedPreferences.SHARED_PREFS_NAME
import com.example.data.storage.sharedpreferences.models.Theme
import com.example.data.storage.sharedpreferences.repository.SharedPreferencesRepository

class SharedPreferencesTheme(private val context: Context):SharedPreferencesRepository{
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    override fun saveTheme(theme: Theme) {
        sharedPreferences.edit().putString(KEY_TOOLBAR_COLOR,theme.toolbarColor.toString())
            .apply()
        sharedPreferences.edit().putString(KEY_BACKGROUND_NOTEPAD_COLOR,theme.backgroundNotepadColor.toString())
            .apply()
        sharedPreferences.edit().putString(KEY_BACKGROUND_NOTE_COLOR,theme.backgroundNoteColor.toString())
            .apply()

    }


    override fun getTheme(): Theme {
        val toolbarColor  = sharedPreferences.getString(
            KEY_TOOLBAR_COLOR,
            "$BASE_TOOLBAR_COLOR")
            ?: "$BASE_TOOLBAR_COLOR"
        val backgroundNotepadColor  = sharedPreferences.getString(
            KEY_BACKGROUND_NOTEPAD_COLOR,
            "$BASE_NOTEPAD_COLOR"
        )?:"$BASE_NOTEPAD_COLOR"
        val backgroundNoteColor  = sharedPreferences.getString(
            KEY_BACKGROUND_NOTE_COLOR,
            "$BASE_NOTE_COLOR"
        )?: "$BASE_NOTE_COLOR"

        return Theme(toolbarColor.toInt(),backgroundNotepadColor.toInt(),backgroundNoteColor.toInt())
    }

    override fun saveCurrentButton(id: Int) {
        sharedPreferences.edit().putString(KEY_CURRENT_BUTTON,id.toString()).apply()
    }

    override fun getCurrentButton(): Int {
        val id = sharedPreferences.getString(KEY_CURRENT_BUTTON,"$BASE_CURRENT_BUTTON")?:"$BASE_CURRENT_BUTTON"
        return id.toInt()
    }

}