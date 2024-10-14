package com.example.moviesearch.presentation.ui.filterSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilterSearchViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()
    fun loadViewedMovie(
        filmId: Int,
        nameMovie: String,
        genre: String,
        ratingKinopoisk: Double,
        uriStringPhoto: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).loadViewedMovie(
                filmId,
                nameMovie,
                genre,
                ratingKinopoisk,
                uriStringPhoto
            )
        }
    }
    suspend fun getContries(idContries: Int): String {
        var getStringContries = ""
        val getListCountry = MovieFromKinopoiskUseCase().executeListGenryCountry().countries
        getListCountry.forEach {
            if (it.id == idContries) {
                val index = getListCountry.indexOf(it)
                getStringContries = getListCountry.get(index).country
            }
        }
        return getStringContries
    }

    suspend fun getGenre(idGenre: Int): String {
        var getStringGenre = ""
        val getListGenre = MovieFromKinopoiskUseCase().executeListGenryCountry().genres
        getListGenre.forEach {
            if (it.id == idGenre) {
                val index = getListGenre.indexOf(it)
                getStringGenre = getListGenre.get(index).genre
            }
        }
        return getStringGenre
    }
}