package com.example.moviesearch.presentation.ui.addToCollection.createCollection

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.DataBaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateCollectionViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    fun getCollection(): Flow<List<Collection>> {
        return DataBaseUseCase(movieDao).getAllCollection()
    }
    fun getCollectionList(): List<Collection> {
        return DataBaseUseCase(movieDao).getAllCollectionList()
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