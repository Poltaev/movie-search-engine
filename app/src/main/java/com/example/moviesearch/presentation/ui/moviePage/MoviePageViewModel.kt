package com.example.moviesearch.presentation.ui.moviePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.dataBase.FilmForCollection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.IdCollectionAndMovie
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import com.example.moviesearch.entity.filmIdInformation.Countries
import com.example.moviesearch.entity.filmIdInformation.FilmIdInformaation
import com.example.moviesearch.entity.filmIdInformation.Genres
import com.example.moviesearch.entity.listPhotoGallery.Items
import com.example.moviesearch.entity.similars.SimilarMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviePageViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    fun getAllViewedMovie(): Flow<List<ViewedMovie>> {
        return DataBaseUseCase(movieDao).getAllViewedMovie()
    }
    suspend  fun deleteViewedMovie (viewedMovie: ViewedMovie) {
        DataBaseUseCase(movieDao).deleteViewedMovie(viewedMovie)
    }
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


    fun loadActorMovie(
        filmIdActor: Int,
        nameMovieActor: String,
        genreActor: String,
        uriStringPhotoActor: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).loadActorMovie(
                filmIdActor,
                nameMovieActor,
                genreActor,
                uriStringPhotoActor
            )
        }
    }


    suspend fun filmInformation(filmId: Int): FilmIdInformaation {
        return MovieFromKinopoiskUseCase().executeFilmIdInformation(filmId)
    }

    suspend fun getListActor(filmId: Int): List<ActorDirector> {
        return MovieFromKinopoiskUseCase().executeActorDirector(filmId)
    }

    suspend fun getListDirector(filmId: Int): List<ActorDirector> {
        return MovieFromKinopoiskUseCase().executeActorDirector(filmId)
    }

    suspend fun getListGallery(id: Int, type: String): List<Items> {
        return MovieFromKinopoiskUseCase().executeGallery(id, type).items
    }

    suspend fun getListRelatedMovies(filmId: Int): List<SimilarMovie> {
        return MovieFromKinopoiskUseCase().executeSimilarsFilm(filmId).items
    }

    fun translatorOfMinutesIntoHours(getMinutes: Int): String {
        var clockСounter = 0
        var clockMinutes = getMinutes
        while (clockMinutes >= 60) {
            clockMinutes = clockMinutes - 60
            clockСounter = clockСounter + 1
        }
        val hoursMinutes = "${clockСounter}" + " ч " + "${clockMinutes}" + " мин"
        return hoursMinutes
    }

    fun getAllCountry(listcontryGemre: List<Countries>): String {
        var allCountry = ""
        listcontryGemre.forEach {
            allCountry = allCountry + "${it.country}, "
        }
        return allCountry
    }

    fun getAllGenre(listcontryGemre: List<Genres>): String {
        var allGenre = ""
        listcontryGemre.forEach {
            allGenre = allGenre + "${it.genre}, "
        }
        return allGenre
    }

    fun getShortDescription(description: String): String {
        var shortDescription = ""
        val lineLength = description.count()
        if (lineLength <= 250) {
            return description
        } else {
            var char: MutableList<Char> = description.toMutableList()
            var charSize = char.size
            while (charSize > 250) {
                char.removeLast()
                charSize -= 1
            }
            char.add('.')
            char.add('.')
            char.add('.')
            shortDescription = char.joinToString("")
        }
        return shortDescription
    }
    fun getAllCollectionMovie(): Flow<List<IdCollectionAndMovie>> {
        return DataBaseUseCase(movieDao).getAllFilmInThisCollection()
    }
    suspend  fun deleteCollectionMovie (idFromCollection: IdCollectionAndMovie) {
        DataBaseUseCase(movieDao).deletefilmFromCollection(idFromCollection)
    }
    fun loadInCollectionMovie(
        collectionId: Int,
        filmId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).loadFilmToTheCollection(
                collectionId,
                filmId
            )
        }
    }
    fun getAllSavedFilm(): Flow<List<FilmForCollection>> {
        return DataBaseUseCase(movieDao).getAllSevedFilm()
    }
    suspend  fun deleteWantToSeeMovie (sevedMovie: FilmForCollection) {
        DataBaseUseCase(movieDao).deleteSevedFilm(sevedMovie)
    }
    fun loadWantToSeeMovie(
        filmId: Int,
        nameMovie: String,
        genre: String,
        ratingKinopoisk: Double,
        uriStringPhoto: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).loadSevedFilm(
                filmId,
                nameMovie,
                genre,
                ratingKinopoisk,
                uriStringPhoto
            )
        }
    }
}