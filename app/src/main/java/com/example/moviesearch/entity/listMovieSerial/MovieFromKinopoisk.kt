package com.example.moviesearch.entity.listMovieSerial

data class MovieFromKinopoisk(
    val kinopoiskId: Int,
    val imdbId: String,
    val nameRu: String,
    val nameOriginal: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val ratingKinopoisk: Double,
    val year: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val description: String
)
