package com.example.data.repository

import com.example.data.storage.NoteDao
import com.example.data.storage.entity.Note
import kotlinx.coroutines.flow.Flow

class NotepadRepositoryImpl(private val noteDao: NoteDao):NotepadRepository {
    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(id:Int) {
        noteDao.deleteNote(id)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override suspend fun getNote(id: Int): Note {
        return noteDao.getNote(id)
    }

    override suspend fun findNoteByTitle(title:String): Flow<List<Note>> {
        return noteDao.findNoteByTitle(title)
    }

    override suspend fun getAllNote(): Flow<List<Note>> {
        return noteDao.getAllNote()
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }


}