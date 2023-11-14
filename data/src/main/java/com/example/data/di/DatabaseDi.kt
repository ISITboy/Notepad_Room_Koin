package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.storage.ConstantsDatabase.DATABASE_NAME
import com.example.data.storage.room.NoteDatabase

fun providerDatabase(context: Context)=
    Room.databaseBuilder(
        context = context,
        klass = NoteDatabase::class.java,
        name = DATABASE_NAME
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

fun providerDao (db:NoteDatabase) = db.noteDao()