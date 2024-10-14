package com.example.moviesearch.domain

import com.example.moviesearch.data.MovieFromKinopoiskRepository
import com.example.moviesearch.entity.SerialSeries.InformationSerialSeries
import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import com.example.moviesearch.entity.filmIdInformation.FilmIdInformaation
import com.example.moviesearch.entity.foundMovies.FoundMovies
import com.example.moviesearch.entity.informationPerson.InformationPerson
import com.example.moviesearch.entity.listContryGenre.ListIdCountryGenre
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoiskForDownloading
import com.example.moviesearch.entity.listPhotoGallery.ListPhotoGallery
import com.example.moviesearch.entity.similars.MovieFromKinopoiskSimilars

class MovieFromKinopoiskUseCase {

    private val repo = MovieFromKinopoiskRepository()

    suspend fun executeTop(): MovieFromKinopoiskForDownloading {
        return repo.getMovieFromKinopoiskTop()
    }

    suspend fun executeTop250(): MovieFromKinopoiskForDownloading {
        return repo.getMovieFromKinopoiskTop250()
    }

    suspend fun executePrimeres(): MovieFromKinopoiskForDownloading {
        return repo.getMovieFromKinopoiskPrimieres()
    }

    suspend fun executeUSMilitants(): MovieFromKinopoiskForDownloading {
        return repo.getMovieFromKinopoiskUSMilitants()
    }

    suspend fun executeDramasOfFrance(): MovieFromKinopoiskForDownloading {
        return repo.getMovieFromKinopoiskDramasOfFrance()
    }

    suspend fun executeSeries(): MovieFromKinopoiskForDownloading {
        return repo.getMovieFromKinopoiskSeries()
    }

    suspend fun executeActorDirector(filmId: Int): List<ActorDirector> {
        return repo.getActorDirector(filmId)
    }

    suspend fun executeGallery(id: Int, type: String): ListPhotoGallery {
        return repo.getGallery(id, type)
    }

    suspend fun executeSerialSeason(id: Int): InformationSerialSeries {
        return repo.getSerialSeason(id)
    }

    suspend fun executeFilmIdInformation(id: Int): FilmIdInformaation {
        return repo.getFilmIdInformation(id)
    }

    suspend fun executeSimilarsFilm(id: Int): MovieFromKinopoiskSimilars {
        return repo.getSimilarsMovies(id)
    }

    suspend fun executePersonInformation(id: Int): InformationPerson {
        return repo.getInformationPerson(id)
    }

    suspend fun executeFoundMovie(
        countries: Int,
        genres: Int,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        keyword: String
    ): FoundMovies {
        return repo.getFoundMovie(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            keyword)
    }
    suspend fun executeListGenryCountry(): ListIdCountryGenre {
        return repo.getListGenreCountry()
    }
}