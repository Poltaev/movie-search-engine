package com.example.moviesearch.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "viewedMovieAndSerial")
data class ViewedMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val filmId: Int,
    val nameMovie: String,
    val ratingKinopoisk: Double,
    val genre: String,
    val uriStringPhoto: String,
)