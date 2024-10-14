package com.example.moviesearch.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_film")
data class FilmForCollection(
    val id: Int,
    @PrimaryKey
    @ColumnInfo(name = "film_id")
    val filmId: Int,
    val nameMovie: String,
    val ratingKinopoisk: Double,
    val genre: String,
    val uriStringPhoto: String,
)
