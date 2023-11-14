package com.example.myapplication.di

import com.example.myapplication.presentation.mainScreen.MainViewModel
import com.example.myapplication.presentation.themeScreen.ThemeFragmentViewModel
import org.koin.dsl.module


val appModule = module{
    single<MainViewModel>{
        MainViewModel(notepadRepository = get())
    }
    single<ThemeFragmentViewModel>{
        ThemeFragmentViewModel(sharedPreferencesTheme = get())
    }
}