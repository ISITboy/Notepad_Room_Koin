package com.example.data.repository

import com.example.data.storage.entity.Note
import kotlinx.coroutines.flow.Flow

interface NotepadRepository {
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(id:Int)
    suspend fun updateNote(note: Note)
    suspend fun getNote(id: Int): Note
    suspend fun findNoteByTitle(title:String): Flow<List<Note>>
    suspend fun getAllNote(): Flow<List<Note>>
    suspend fun deleteAllNotes()
}