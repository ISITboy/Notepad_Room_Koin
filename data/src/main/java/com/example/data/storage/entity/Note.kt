package com.example.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.storage.ConstantsDatabase.TABLE_NOTE

@Entity(tableName = TABLE_NOTE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "description")
    var description: String = ""
)
