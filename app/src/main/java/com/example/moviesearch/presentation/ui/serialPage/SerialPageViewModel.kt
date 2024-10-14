package com.example.moviesearch.presentation.ui.serialPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.dataBase.FilmForCollection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.IdCollectionAndMovie
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.SerialSeries.Season
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

class SerialPageViewModel(private val movieDao: MovieDao) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    suspend fun getAllViewedMovie(): Flow<List<ViewedMovie>> {
        return DataBaseUseCase(movieDao).getAllViewedMovie()
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


    fun deleteAllViewedMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.getAllViewedMoviesList().forEach {
                movieDao.deleteViewedMovies(it)
            }
        }
    }

    suspend fun getAllActorMovie(): Flow<List<HistoryMovieActor>> {
        return DataBaseUseCase(movieDao).getAllActorMovie()
    }

    suspend fun loadActorMovie(
        filmIdActor: Int,
        nameMovieActor: String,
        genreActor: String,
        uriStringPhotoActor: String
    ) {
        DataBaseUseCase(movieDao).loadActorMovie(
            filmIdActor,
            nameMovieActor,
            genreActor,
            uriStringPhotoActor
        )
    }


    fun deleteAllActorMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            movieDao.getAllActorMoviesList().forEach {
                movieDao.deleteActorMovies(it)
            }
        }
    }

    suspend fun getListSerials(id: Int): List<Season> {
        return MovieFromKinopoiskUseCase().executeSerialSeason(id).items
    }
    suspend fun getFiilmInformation(id: Int): FilmIdInformaation {
        return MovieFromKinopoiskUseCase().executeFilmIdInformation(id)
    }
    suspend fun getListActor(id: Int):  List<ActorDirector> {
        return MovieFromKinopoiskUseCase().executeActorDirector(id)
    }
    suspend fun getListDirector(id: Int):  List<ActorDirector> {
        return MovieFromKinopoiskUseCase().executeActorDirector(id)
    }
    suspend fun getListGallery(id: Int, type : String):   List<Items> {
        return MovieFromKinopoiskUseCase().executeGallery(id, type).items
    }
    suspend fun getListRelatedMovies(id: Int):    List<SimilarMovie> {
        return MovieFromKinopoiskUseCase().executeSimilarsFilm(id).items
    }

     fun translatorOfMinutesIntoHours(getMinutes: Int): String {
        var getIntMinutes = getMinutes
        var clockСounter = 0
        while (getIntMinutes >= 60) {
            getIntMinutes = getIntMinutes - 60
            clockСounter = clockСounter++
        }
        val hoursMinutes = "${clockСounter} ч ${getIntMinutes} мин"
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
        if (description != null) {
            var shortDescription = ""
            val lineLength = description.count()
            if (lineLength <= 250) {
                return description
            } else {
                val char: MutableList<Char> = description.toMutableList()
                var charSize = char.size
                while (charSize <= 247) {
                    char.removeLast()
                    charSize -= 1
                }
                char.add('.')
                char.add('.')
                char.add('.')
                shortDescription = char.joinToString("")
            }
            return shortDescription
        } else return ""
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
    suspend  fun deleteViewedMovie (viewedMovie: ViewedMovie) {
        DataBaseUseCase(movieDao).deleteViewedMovie(viewedMovie)
    }
}