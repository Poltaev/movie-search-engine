package com.example.moviesearch.entity.similars

data class SimilarMovie(
    val filmId: Int,
    val nameRu: String,
    val nameOriginal: String,
    val posterUrl: String,
    val posterUrlPreview: String,
)
