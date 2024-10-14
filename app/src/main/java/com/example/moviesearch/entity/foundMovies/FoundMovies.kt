package com.example.moviesearch.entity.foundMovies

interface FoundMovies {
    val total : Int
    val totalPages: Int
    val items : List<FoundMovie>
}