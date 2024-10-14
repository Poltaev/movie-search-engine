package com.example.moviesearch.presentation.ui.first_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.IdCollectionAndMovie
import com.example.moviesearch.dataBase.IndexForWeKnewAboutFirstUse
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.DataBaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FirstActivityViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

    fun loadFirstDataBase(
        id: Int,
        index: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).loadIndex(
                id,
                index
            )
        }
    }
    fun upDateFirstDataBase(
        id: Int,
        index: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            DataBaseUseCase(movieDao).upDateIndex(
                id,
                index
            )
        }
    }

    fun getIndex(): Flow<List<IndexForWeKnewAboutFirstUse>> {
        return DataBaseUseCase(movieDao).getAllIndex()
    }
}