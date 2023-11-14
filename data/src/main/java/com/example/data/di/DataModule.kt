package com.example.data.di

import com.example.data.repository.NotepadRepositoryImpl
import com.example.data.storage.sharedpreferences.SharedPreferencesTheme
import org.koin.dsl.module

val dataModule = module {
    single { providerDatabase(context = get()) }
    single { providerDao(db = get()) }
    single { NotepadRepositoryImpl(noteDao = get()) }
    single { SharedPreferencesTheme(context = get()) }
}