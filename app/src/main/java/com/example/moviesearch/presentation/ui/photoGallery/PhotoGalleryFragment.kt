package com.example.moviesearch.presentation.ui.photoGallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.databinding.FragmentPhotoGalleryBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.presentation.GalleryAdapter
import com.example.moviesearch.presentation.ui.adapters.GalleryButtonAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch

class PhotoGalleryFragment : Fragment() {
    private var filmId: Int? = 535341
    private val listTupePhotoButton =
        listOf("Стоп-кадры", "Со съемок", "Постеры", "Промо", "Фан-Арты", "Концепт-Арты")

    private val viewModel: PhotoGalleryViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return PhotoGalleryViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentPhotoGalleryBinding? = null

    private val binding: FragmentPhotoGalleryBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmId = it.getInt("filmId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoGalleryBinding.inflate(inflater)
        lifecycleScope.launch {
            binding.buttonBack.setOnClickListener {
                lifecycleScope.launch {
                    val bundle = Bundle()
                    if (filmId != null) {
                        bundle.putInt("filmId", filmId!!)
                    }
                    val typeContent =
                        filmId?.let { viewModel.typeContent(it) }
                    if (typeContent != null) {
                        if (typeContent.serial) {
                            findNavController().navigate(
                                R.id.action_photoGalleryFragment_to_serialPageFragment,
                                bundle
                            )
                        } else {
                            findNavController().navigate(
                                R.id.action_photoGalleryFragment_to_moviePageFragment,
                                bundle
                            )
                        }
                    }
                }
            }
            binding.recyclerViewButtonFilterGallery.adapter =
                listTupePhotoButton.let { GalleryButtonAdapter(it) { onItemClick(it) } }

            val getListGallery =
                filmId?.let { viewModel.getListGallery(it, "STILL") }
            val GalleryAdapter = getListGallery?.let { GalleryAdapter(it) }
            binding.recyclerPhotoGallery.adapter = GalleryAdapter
        }
        return binding.root
    }

    private fun onItemClick(item: String) {
        lifecycleScope.launch {
            var getTypePhoto = ""
            when (item) {
                "Стоп-кадры" -> {
                    getTypePhoto = "STILL"
                }

                "Со съемок" -> {
                    getTypePhoto = "SHOOTING"
                }

                "Постеры" -> {
                    getTypePhoto = "POSTER"
                }

                "Промо" -> {
                    getTypePhoto = "PROMO"
                }

                "Фан-Арты" -> {
                    getTypePhoto = "FAN_ART"
                }

                "Концепт-Арты" -> {
                    getTypePhoto = "CONCEPT"
                }
            }
            val getListGallery =
                filmId?.let { viewModel.getListGallery(it, getTypePhoto) }
            val GalleryAdapter = getListGallery?.let { GalleryAdapter(it) }
            binding.recyclerPhotoGallery.adapter = GalleryAdapter
        }
    }
}