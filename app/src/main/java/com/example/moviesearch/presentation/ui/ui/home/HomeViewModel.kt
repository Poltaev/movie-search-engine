package com.example.moviesearch.presentation.ui.ui.home

import androidx.lifecycle.ViewModel
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listMovieSerial.Country
import com.example.moviesearch.entity.listMovieSerial.Genre
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    suspend fun getListMovieTop(): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeTop().items
    }

    suspend fun getListMovieTop250(): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeTop250().items
    }

    suspend fun getListMoviePrimeres(): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executePrimeres().items
    }

    suspend fun getListMovieUSMilitants(): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeUSMilitants().items
    }

    suspend fun getListMovieDramasOfFrance(): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeDramasOfFrance().items
    }

    suspend fun getListMovieSeries(): List<MovieFromKinopoisk> {
        return MovieFromKinopoiskUseCase().executeSeries().items
    }

    fun get15ItemsInTheMovieList(listFilm: List<MovieFromKinopoisk>, collectionType : String): MutableList<MovieFromKinopoisk> {
        val mutableListFilmTop = listFilm.toMutableList()
        while (mutableListFilmTop.size > 15) {
            var lastIndex = mutableListFilmTop.lastIndex
            mutableListFilmTop.removeAt(lastIndex)
        }
        val listContry = listOf<Country>()
        val genre = Genre("1111")
        val listGenre = listOf(genre)
        val lastMovieFromKinopoisk =
            MovieFromKinopoisk(1, "1", "1", "1",
                listContry, listGenre, 1.1, "1", "R.drawable.right_arrow",
                "1", collectionType)
        mutableListFilmTop.add(lastMovieFromKinopoisk)
        return mutableListFilmTop
    }
    suspend fun loadCollection(
        id: Int,
        nameCollection: String,
        sizeCollection: Int,
        frontPageCollection: String
    ) {
        DataBaseUseCase(movieDao).loadCollection(
            id,
            nameCollection,
            sizeCollection,
            frontPageCollection
        )
    }
}