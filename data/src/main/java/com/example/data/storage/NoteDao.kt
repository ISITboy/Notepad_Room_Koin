package com.example.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.storage.ConstantsDatabase.TABLE_NOTE
import com.example.data.storage.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM $TABLE_NOTE WHERE id = :id")
    suspend fun deleteNote(id:Int)

    @Query("SELECT * FROM $TABLE_NOTE WHERE id = :id")
    suspend fun getNote(id: Int): Note

    @Query("SELECT * FROM $TABLE_NOTE WHERE title LIKE '%' || :searchTitle || '%'")
    fun findNoteByTitle(searchTitle: String):Flow<List<Note>>

    @Query("SELECT * FROM $TABLE_NOTE")
    fun getAllNote(): Flow<List<Note>>

    @Query("DELETE FROM $TABLE_NOTE")
    suspend fun deleteAllNotes()

}