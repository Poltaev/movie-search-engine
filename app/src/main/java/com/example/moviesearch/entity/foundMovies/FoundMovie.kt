package com.example.moviesearch.entity.foundMovies

import com.example.moviesearch.entity.listMovieSerial.Country
import com.example.moviesearch.entity.listMovieSerial.Genre

data class FoundMovie(
    val kinopoiskId: Int,
    val imdbId: String,
    val nameRu: String,
    val nameOriginal: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val ratingKinopoisk: Double,
    val year: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
)
