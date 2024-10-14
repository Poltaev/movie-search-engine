package com.example.moviesearch.presentation.ui.listContent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.databinding.FragmentListContentBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import com.example.moviesearch.presentation.ui.adapters.MovieHomeFromKinopoiskAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch


class ListContentFragment : Fragment() {
    private var collection: String? = null

    private val viewModel: ListContentViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return ListContentViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentListContentBinding? = null

    private val binding: FragmentListContentBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collection = it.getString("CollectionType")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListContentBinding.inflate(inflater)
        binding.textHeadingContentFragment.text = collection
        lifecycleScope.launch {
            when (collection) {
                "Премьеры" -> {
                    val getListMoviePrimeres = viewModel.getListMoviePrimeres()
                    val movieHomeAdapterPrimeres =
                        MovieHomeFromKinopoiskAdapter(getListMoviePrimeres) { onItemClickFilm(it) }
                    binding.recyclerViewCollectionContent.adapter = movieHomeAdapterPrimeres
                }

                "Популярное" -> {
                    val getListMovieTop = viewModel.getListMovieTop()
                    val movieHomeAdapterTop =
                        MovieHomeFromKinopoiskAdapter(getListMovieTop) { onItemClickFilm(it) }
                    binding.recyclerViewCollectionContent.adapter = movieHomeAdapterTop
                }

                "Боевики США" -> {
                    val getListMovieUSMilitants = viewModel.getListMovieUSMilitants()
                    val movieHomeAdapterUSMilitants =
                        MovieHomeFromKinopoiskAdapter(
                            getListMovieUSMilitants
                        ) { onItemClickFilm(it) }
                    binding.recyclerViewCollectionContent.adapter = movieHomeAdapterUSMilitants
                }

                "Топ-250" -> {
                    val getListMovieTop250 = viewModel.getListMovieTop250()
                    val movieHomeAdapterTop250 =
                        MovieHomeFromKinopoiskAdapter(getListMovieTop250) { onItemClickFilm(it) }
                    binding.recyclerViewCollectionContent.adapter = movieHomeAdapterTop250
                }

                "Драмы Франции" -> {
                    val getListMovieDramasOfFrance = viewModel.getListMovieDramasOfFrance()
                    val movieHomeAdapterDramasOfFrance =
                        MovieHomeFromKinopoiskAdapter(
                            getListMovieDramasOfFrance
                        ) { onItemClickFilm(it) }
                    binding.recyclerViewCollectionContent.adapter = movieHomeAdapterDramasOfFrance
                }

                "Сериалы" -> {
                    val getListMovieSeries = viewModel.getListMovieSeries()
                    val movieHomeAdapterSeries =
                        MovieHomeFromKinopoiskAdapter(getListMovieSeries) { onItemClickSerial(it) }
                    binding.recyclerViewCollectionContent.adapter = movieHomeAdapterSeries
                }
            }
        }

        binding.buttonBackContentFragmenttoHomeFragment.setOnClickListener {
            findNavController().navigate(com.example.moviesearch.R.id.action_listContentFragment_to_navigation_home)
        }
        return binding.root
    }

    private fun onItemClickFilm(item: MovieFromKinopoisk) {
        val bundle = Bundle().apply {
            putInt("FilmId", item.kinopoiskId)
        }
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_listContentFragment_to_moviePageFragment, bundle)
    }

    private fun onItemClickSerial(item: MovieFromKinopoisk) {
        val bundle = Bundle().apply {
            putInt("FilmId", item.kinopoiskId)
        }
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_listContentFragment_to_serialPageFragment, bundle)
    }
}