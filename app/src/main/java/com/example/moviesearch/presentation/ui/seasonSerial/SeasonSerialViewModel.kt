package com.example.moviesearch.presentation.ui.seasonSerial

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.SerialSeries.InformationSerialSeries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SeasonSerialViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

suspend fun getListSerialSeason (id : Int): InformationSerialSeries {
    return MovieFromKinopoiskUseCase().executeSerialSeason(id)
}

}