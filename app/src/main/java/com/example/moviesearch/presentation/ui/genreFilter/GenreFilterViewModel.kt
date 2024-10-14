package com.example.moviesearch.presentation.ui.genreFilter

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listContryGenre.IdGenre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GenreFilterViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    suspend fun getListGenre(): List<IdGenre> {
        return MovieFromKinopoiskUseCase().executeListGenryCountry().genres
    }
}