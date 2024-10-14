package com.example.moviesearch.presentation.ui.ui.profile

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.entity.listMovieSerial.Country
import com.example.moviesearch.entity.listMovieSerial.Genre
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val movieDao: MovieDao) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    fun get15ItemsInTheMovieViewedList(listFilm: List<ViewedMovie>): MutableList<ViewedMovie> {
        val mutableListFilmTop = listFilm.toMutableList()
        while (mutableListFilmTop.size > 15) {
            var lastIndex = mutableListFilmTop.lastIndex
            mutableListFilmTop.removeAt(lastIndex)
        }
        val lastMovieFromKinopoisk =
            ViewedMovie(1, 1, "11111", 1.1, "1", "1")
        mutableListFilmTop.add(lastMovieFromKinopoisk)
        return mutableListFilmTop
    }

    fun get15ItemsInTheMovieViewedHistoryList(listFilm: List<HistoryMovieActor>): MutableList<HistoryMovieActor> {
        val mutableListFilmTop = listFilm.toMutableList()
        while (mutableListFilmTop.size > 15) {
            var lastIndex = mutableListFilmTop.lastIndex
            mutableListFilmTop.removeAt(lastIndex)
        }
        val lastMovieFromKinopoisk =
            HistoryMovieActor(1, 1, "11111", "1.1", "1")
        mutableListFilmTop.add(lastMovieFromKinopoisk)
        return mutableListFilmTop
    }

    fun getAllViewedMovie(): Flow<List<ViewedMovie>> {
        return DataBaseUseCase(movieDao).getAllViewedMovie()
    }


    fun deleteAllViewedMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).deleteAllViewedMovie()
        }
    }

    fun getHistoryMovie(): Flow<List<HistoryMovieActor>> {
        return DataBaseUseCase(movieDao).getAllActorMovie()
    }


    fun deleteAllActorMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).deleteAllActorMovie()
        }
    }

    fun getCollection(): Flow<List<Collection>> {
        return DataBaseUseCase(movieDao).getAllCollection()
    }


    suspend fun loadCollection(
        id: Int,
        nameCollection: String,
        sizeCollection: Int,
        frontPageCollection: String
    ) {
        DataBaseUseCase(movieDao).loadCollection(
            id,
            nameCollection,
            sizeCollection,
            frontPageCollection
        )
    }


    suspend fun deleteCollection(collection: Collection) {
        DataBaseUseCase(movieDao).deleteCollection(collection)
    }


}