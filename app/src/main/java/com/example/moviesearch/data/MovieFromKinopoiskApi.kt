package com.example.moviesearch.data

import android.telecom.Call
import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieFromKinopoiskApi {
    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/collections")
    suspend fun getMovieFromKinopoiskTop(): MovieFromKinopoiskDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/collections")
    suspend fun getMovieFromKinopoiskTop250(@Query("type") type: String): MovieFromKinopoiskDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/premieres")
    suspend fun getMovieFromKinopoiskPrimieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): MovieFromKinopoiskDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun getMovieFromKinopoiskUSMilitants(
        @Query("genres") genres: Int,
        @Query("countries") countries: Int
    ): MovieFromKinopoiskDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun getMovieFromKinopoiskDramasOfFrance(
        @Query("genres") genres: Int,
        @Query("countries") countries: Int
    ): MovieFromKinopoiskDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/collections")
    suspend fun getMovieFromKinopoiskSerias(@Query("type") type: String): MovieFromKinopoiskDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff")
    suspend fun getActorDirector(@Query("filmId") filmId: Int): List<ActorDirector>

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun getGallery(@Path("id") id: Int, @Query("type") type: String): PhotoGalleryDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getRelatedMovies(@Path("id") id: Int): MovieSimilarsDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSerialSeason(@Path("id") id: Int): SeasonSerialDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmIdInformation(@Path("id") id: Int): FilmIdInformationDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v1/staff/{id}")
    suspend fun getPersonInformation(@Path("id") id: Int): PersonInformationDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films")
    suspend fun getFoundMovies(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("keyword") keyword: String
    ): FoundMovieDto

    @Headers("X-API-KEY: $api_key")
    @GET("/api/v2.2/films/filters")
    suspend fun getListGenreCountries(): ListCountryGenreDto

    private companion object {
        private const val api_key = "f393e4a7-2d56-4b29-8813-42e5c4abf09a"
        private const val api_key1 = "12b9dfe2-6c30-44a0-ae20-f02aa854202e"
    }
}
