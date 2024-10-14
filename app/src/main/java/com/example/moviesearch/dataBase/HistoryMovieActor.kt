package com.example.moviesearch.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historyMovieActor")
data class HistoryMovieActor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val filmIdActor : Int,
    val nameMovieActor : String,
    val genreActor : String,
    val uriStringPhotoActor : String,
)
