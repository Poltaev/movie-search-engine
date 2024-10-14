package com.example.moviesearch.presentation.ui.filmography

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.informationPerson.InformationPerson
import com.example.moviesearch.entity.informationPerson.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilmographyViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    suspend fun getInformationPerson(staffId: Int): InformationPerson {
        return MovieFromKinopoiskUseCase().executePersonInformation(staffId)
    }

    suspend fun getInformationPersonFilm(staffId: Int): List<Movie> {
        return MovieFromKinopoiskUseCase().executePersonInformation(staffId).films
    }
    suspend fun getListUriPhoto(listMovie: List<Movie>): List<String> {
        val listFilmId = mutableListOf<Int>()
        listMovie.forEach {
            listFilmId.add(it.filmId)
        }
        val listUriForGlige = mutableListOf<String>()
        listFilmId.forEach {
            listUriForGlige.add(MovieFromKinopoiskUseCase().executeFilmIdInformation(it).posterUrl)
        }
        return listUriForGlige
    }
}