package com.example.moviesearch.presentation.ui.ui.search

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.foundMovies.FoundMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    suspend fun getInformationPersonFilm(
        contries: Int,
        genres: Int,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        keyword: String
    ): List<FoundMovie> {
        return MovieFromKinopoiskUseCase().executeFoundMovie(
            contries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            keyword
        ).items
    }

    fun getAllViewedMovie(): Flow<List<ViewedMovie>> {
        return DataBaseUseCase(movieDao).getAllViewedMovie()
    }

    fun getListWithoutViewedMovie(
        viewedMovieList: List<ViewedMovie>,
        listFoundMovies: List<FoundMovie>
    ): List<FoundMovie> {
        var coincidenceMovie = listFoundMovies[0]
        val foundMoviesToMutableList = listFoundMovies.toMutableList()
        val sizeViewedList = viewedMovieList.size
        for (i in 0..sizeViewedList) {
            foundMoviesToMutableList.forEach {
                if (viewedMovieList[i].filmId == it.kinopoiskId){
                    coincidenceMovie = it
                }
            }
            foundMoviesToMutableList.remove(coincidenceMovie)
        }
        return foundMoviesToMutableList.toList()
    }
}