package com.example.moviesearch.presentation.ui.photoGallery

import androidx.lifecycle.ViewModel
import com.example.moviesearch.dataBase.MovieDao
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.filmIdInformation.FilmIdInformaation
import com.example.moviesearch.entity.listPhotoGallery.Items
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotoGalleryViewModel(private val movieDao: MovieDao) : ViewModel() {


    private val _state = MutableStateFlow<State>(State.GetListCollection)
    val state = _state.asStateFlow()

   suspend fun typeContent (filmId: Int): FilmIdInformaation {
       return MovieFromKinopoiskUseCase().executeFilmIdInformation(filmId)
   }
    suspend fun getListGallery (filmId: Int, Type: String):  List<Items> {
        return MovieFromKinopoiskUseCase().executeGallery(filmId, Type).items
    }
}