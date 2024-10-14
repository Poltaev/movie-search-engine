package com.example.moviesearch.presentation.ui.listContent

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListContentViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

   suspend fun getListMoviePrimeres (): List<MovieFromKinopoisk> {
       return MovieFromKinopoiskUseCase().executePrimeres().items
   }
    suspend fun getListMovieTop (): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeTop().items
    }
    suspend fun getListMovieUSMilitants (): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeUSMilitants().items
    }
    suspend fun getListMovieTop250 (): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeTop250().items
    }
    suspend fun getListMovieDramasOfFrance (): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeDramasOfFrance().items
    }
    suspend fun getListMovieSeries (): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeSeries().items
    }
}