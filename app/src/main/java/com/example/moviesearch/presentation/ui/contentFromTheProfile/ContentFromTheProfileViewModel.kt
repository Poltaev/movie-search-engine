package com.example.moviesearch.presentation.ui.contentFromTheProfile

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.FilmForCollection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.IdCollectionAndMovie
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listContryGenre.IdCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContentFromTheProfileViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    fun getAllViewedMovie(): Flow<List<ViewedMovie>> {
        return DataBaseUseCase(movieDao).getAllViewedMovie()
    }

    fun getHistoryMovie(): Flow<List<HistoryMovieActor>> {
        return DataBaseUseCase(movieDao).getAllActorMovie()
    }

    fun getAllSavedFilm(): Flow<List<FilmForCollection>> {
        return DataBaseUseCase(movieDao).getAllSevedFilm()
    }

    fun getAllCollectionMovie(): Flow<List<IdCollectionAndMovie>> {
        return DataBaseUseCase(movieDao).getAllFilmInThisCollection()
    }
    fun getCollection(): Flow<List<Collection>> {
        return DataBaseUseCase(movieDao).getAllCollection()
    }
}