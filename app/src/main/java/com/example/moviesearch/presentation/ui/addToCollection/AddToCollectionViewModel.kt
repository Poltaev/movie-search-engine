package com.example.moviesearch.presentation.ui.addToCollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.IdCollectionAndMovie
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.DataBaseUseCase
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.filmIdInformation.FilmIdInformaation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddToCollectionViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    fun getCollection(): Flow<List<Collection>> {
        return DataBaseUseCase(movieDao).getAllCollection()
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

    suspend  fun deleteCollectionMovie (idFromCollection: IdCollectionAndMovie) {
        DataBaseUseCase(movieDao).deletefilmFromCollection(idFromCollection)
    }
}