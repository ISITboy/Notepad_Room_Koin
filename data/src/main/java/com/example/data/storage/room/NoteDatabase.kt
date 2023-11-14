package com.example.data.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.storage.NoteDao
import com.example.data.storage.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}