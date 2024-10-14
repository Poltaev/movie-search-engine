package com.example.moviesearch.data

import com.example.moviesearch.entity.foundMovies.FoundMovie
import com.example.moviesearch.entity.foundMovies.FoundMovies
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoiskForDownloading

class FoundMovieDto (
    override val items: List<FoundMovie>,
    override val total: Int,
    override val totalPages: Int
) : FoundMovies