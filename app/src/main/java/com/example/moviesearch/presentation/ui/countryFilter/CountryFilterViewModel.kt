package com.example.moviesearch.presentation.ui.countryFilter

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listContryGenre.IdCountry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountryFilterViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

   suspend fun getListCountry (): List<IdCountry> {
       return MovieFromKinopoiskUseCase().executeListGenryCountry().countries
   }
}