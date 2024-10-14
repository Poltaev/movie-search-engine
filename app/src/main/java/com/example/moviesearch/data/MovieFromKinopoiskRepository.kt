package com.example.moviesearch.data

import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import com.example.moviesearch.entity.listPhotoGallery.ListPhotoGallery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFromKinopoiskRepository {
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit =
        Retrofit.Builder().baseUrl("https://kinopoiskapiunofficial.tech").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

    private val movieApiFromKinopoisk: MovieFromKinopoiskApi =
        retrofit.create(MovieFromKinopoiskApi::class.java)

    suspend fun getMovieFromKinopoiskTop(): MovieFromKinopoiskDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getMovieFromKinopoiskTop()
        return newMovieFromKinopoisk
    }

    suspend fun getMovieFromKinopoiskTop250(): MovieFromKinopoiskDto {
        val newMovieFromKinopoisk =
            movieApiFromKinopoisk.getMovieFromKinopoiskTop250("TOP_250_MOVIES")
        return newMovieFromKinopoisk
    }

    suspend fun getMovieFromKinopoiskPrimieres(): MovieFromKinopoiskDto {
        val newMovieFromKinopoisk =
            movieApiFromKinopoisk.getMovieFromKinopoiskPrimieres(2024, "AUGUST")
        return newMovieFromKinopoisk
    }

    suspend fun getMovieFromKinopoiskUSMilitants(): MovieFromKinopoiskDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getMovieFromKinopoiskUSMilitants(11, 1)
        return newMovieFromKinopoisk
    }

    suspend fun getMovieFromKinopoiskDramasOfFrance(): MovieFromKinopoiskDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getMovieFromKinopoiskDramasOfFrance(2, 3)
        return newMovieFromKinopoisk
    }

    suspend fun getMovieFromKinopoiskSeries(): MovieFromKinopoiskDto {
        val newMovieFromKinopoisk =
            movieApiFromKinopoisk.getMovieFromKinopoiskSerias("POPULAR_SERIES")
        return newMovieFromKinopoisk
    }

    suspend fun getActorDirector(filmId: Int): List<ActorDirector> {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getActorDirector(filmId)
        return newMovieFromKinopoisk
    }

    suspend fun getGallery(id: Int, type: String): ListPhotoGallery {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getGallery(id, type)
        return newMovieFromKinopoisk
    }

    suspend fun getSimilarsMovies(id: Int): MovieSimilarsDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getRelatedMovies(id)
        return newMovieFromKinopoisk
    }

    suspend fun getSerialSeason(id: Int): SeasonSerialDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getSerialSeason(id)
        return newMovieFromKinopoisk
    }

    suspend fun getFilmIdInformation(id: Int): FilmIdInformationDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getFilmIdInformation(id)
        return newMovieFromKinopoisk
    }

    suspend fun getInformationPerson(id: Int): PersonInformationDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getPersonInformation(id)
        return newMovieFromKinopoisk
    }

    suspend fun getFoundMovie(
        countries: Int,
        genres: Int,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        keyword: String
    ): FoundMovieDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getFoundMovies(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            keyword
        )
        return newMovieFromKinopoisk
    }

    suspend fun getListGenreCountry(): ListCountryGenreDto {
        val newMovieFromKinopoisk = movieApiFromKinopoisk.getListGenreCountries()
        return newMovieFromKinopoisk
    }
}