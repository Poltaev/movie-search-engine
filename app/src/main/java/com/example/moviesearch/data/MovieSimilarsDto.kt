package com.example.moviesearch.data

import com.example.moviesearch.entity.similars.SimilarMovie
import com.example.moviesearch.entity.similars.MovieFromKinopoiskSimilars

class MovieSimilarsDto (
    override val items: List<SimilarMovie>
) : MovieFromKinopoiskSimilars {
}