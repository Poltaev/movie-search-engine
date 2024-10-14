package com.example.moviesearch.dataBase

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectionMovieAndSerial")
data class Collection(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val nameCollection: String,
    val sizeCollection: Int,
    val frontPageCollection: String
)
