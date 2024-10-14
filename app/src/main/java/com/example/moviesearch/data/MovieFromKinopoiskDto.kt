package com.example.moviesearch.data

import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoiskForDownloading

class MovieFromKinopoiskDto (
    override val items: List<MovieFromKinopoisk>
) : MovieFromKinopoiskForDownloading