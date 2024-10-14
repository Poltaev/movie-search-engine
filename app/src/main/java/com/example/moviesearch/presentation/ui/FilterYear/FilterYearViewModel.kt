package com.example.moviesearch.presentation.ui.FilterYear

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilterYearViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

   fun getListYearForFilter (): MutableList<Int> {
       var yearList = mutableListOf<Int>()
       for (year in 1985..2030) { yearList.add(year) }
       return yearList
   }
}