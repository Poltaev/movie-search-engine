package com.example.moviesearch.presentation.ui.actorInformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import com.example.moviesearch.entity.filmIdInformation.Countries
import com.example.moviesearch.entity.filmIdInformation.FilmIdInformaation
import com.example.moviesearch.entity.filmIdInformation.Genres
import com.example.moviesearch.entity.informationPerson.InformationPerson
import com.example.moviesearch.entity.informationPerson.Movie
import com.example.moviesearch.entity.listPhotoGallery.Items
import com.example.moviesearch.entity.similars.SimilarMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActorInformationViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    suspend fun getInformationPerson(staffId: Int): InformationPerson {
        return MovieFromKinopoiskUseCase().executePersonInformation(staffId)
    }

    suspend fun getInformationPersonFilm(staffId: Int): List<Movie> {
        return MovieFromKinopoiskUseCase().executePersonInformation(staffId).films
    }

    suspend fun filmInformation(staffId: Int): FilmIdInformaation {
        return MovieFromKinopoiskUseCase().executeFilmIdInformation(staffId)
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

    fun getAlistWith15Values(listMovie: List<Movie>): List<Movie> {
        val mutableList = listMovie.toMutableList()
        while (mutableList.size > 15) {
            var lastIndex = mutableList.lastIndex
            mutableList.removeAt(lastIndex)
        }
        return mutableList.toList()
    }
}