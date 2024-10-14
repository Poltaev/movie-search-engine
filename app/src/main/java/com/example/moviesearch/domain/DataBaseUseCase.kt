package com.example.moviesearch.domain

import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.CollectionWithMovie
import com.example.moviesearch.dataBase.FilmForCollection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.IdCollectionAndMovie
import com.example.moviesearch.dataBase.IndexForWeKnewAboutFirstUse
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.dataBase.ViewedMovie
import kotlinx.coroutines.flow.Flow

class DataBaseUseCase(private val movieDao: MovieDao) {

    suspend fun deleteAllViewedMovie() {
        val getAllphoto = movieDao.getAllViewedMoviesList()
        getAllphoto.forEach {
            movieDao.deleteViewedMovies(it)
        }
    }

    suspend fun deleteViewedMovie(viewedMovie: ViewedMovie) {
        movieDao.deleteViewedMovies(viewedMovie)
    }

    fun getAllViewedMovie(): Flow<List<ViewedMovie>> {
        return movieDao.getAllViewedMoviesFlow()
    }

    suspend fun loadViewedMovie(
        filmId: Int,
        nameMovie: String,
        genre: String,
        ratingKinopoisk: Double,
        uriStringPhoto: String
    ) {
        val viewedMovieForLoad = ViewedMovie(
            filmId = filmId,
            nameMovie = nameMovie,
            ratingKinopoisk = ratingKinopoisk,
            genre = genre,
            uriStringPhoto = uriStringPhoto
        )
        movieDao.insertViewedMovies(viewedMovieForLoad)
    }

    suspend fun deleteAllActorMovie() {
        val getAllphoto = movieDao.getAllActorMoviesList()
        getAllphoto.forEach {
            movieDao.deleteActorMovies(it)
        }
    }

    fun getAllActorMovie(): Flow<List<HistoryMovieActor>> {
        return movieDao.getAllActorMoviesFlow()
    }

    suspend fun loadActorMovie(
        filmIdActor: Int,
        nameMovieActor: String,
        genreActor: String,
        uriStringPhotoActor: String
    ) {
        val historyMovieActorForLoad = HistoryMovieActor(
            filmIdActor = filmIdActor,
            nameMovieActor = nameMovieActor,
            genreActor = genreActor,
            uriStringPhotoActor = uriStringPhotoActor
        )
        movieDao.insertActorMovies(historyMovieActorForLoad)
    }

    suspend fun deleteCollection(collection: Collection) {
        movieDao.deleteCollection(collection)
    }

    fun getAllCollection(): Flow<List<Collection>> {
        return movieDao.getAllCollectionFlow()
    }

    fun getAllCollectionList(): List<Collection> {
        return movieDao.getAllCollectionList()
    }

    suspend fun loadCollection(
        id: Int,
        nameCollection: String,
        sizeCollection: Int,
        forntPageCollection: String
    ) {
        val collectionWithMovie = Collection(
            id,
            nameCollection,
            sizeCollection,
            forntPageCollection
        )
        movieDao.insertCollection(collectionWithMovie)
    }

    suspend fun deleteSevedFilm(sevedMovie: FilmForCollection) {
        movieDao.deleteSavedMovie(sevedMovie)
    }

    fun getAllSevedFilm(): Flow<List<FilmForCollection>> {
        return movieDao.getAllSavedMovieFlow()
    }

    suspend fun loadSevedFilm(
        filmId: Int,
        nameMovie: String,
        genre: String,
        ratingKinopoisk: Double,
        uriStringPhoto: String
    ) {
        val sevedMovieForLoad = FilmForCollection(
            id = filmId,
            filmId = filmId,
            nameMovie = nameMovie,
            ratingKinopoisk = ratingKinopoisk,
            genre = genre,
            uriStringPhoto = uriStringPhoto
        )
        movieDao.insertSavedMovie(sevedMovieForLoad)
    }

    suspend fun deletefilmFromCollection(idCollectionFilm: IdCollectionAndMovie) {
        movieDao.deleteMovieFromCollection(idCollectionFilm)
    }

    fun getAllFilmInThisCollection(): Flow<List<IdCollectionAndMovie>> {
        return movieDao.getAllCollectionWithFilmFlow()
    }

    suspend fun loadFilmToTheCollection(
        collectionId: Int,
        filmId: Int
    ) {
        val sevedMovieForLoad = IdCollectionAndMovie(
            collectionId = collectionId,
            filmId = filmId
        )
        movieDao.insertMovieFromCollection(sevedMovieForLoad)
    }

    fun getAllIndex(): Flow<List<IndexForWeKnewAboutFirstUse>> {
        return movieDao.getIndexFirstUseFlow()
    }

    suspend fun loadIndex(
        id: Int,
        firstUse: String
    ) {
        val firstUse = IndexForWeKnewAboutFirstUse(id, firstUse)
        movieDao.loadIndexFirstUse(firstUse)
    }
    suspend fun upDateIndex(
        id: Int,
        firstUse: String
    ) {
        val firstUse = IndexForWeKnewAboutFirstUse(id, firstUse)
        movieDao.updateIndex(firstUse)
    }
}