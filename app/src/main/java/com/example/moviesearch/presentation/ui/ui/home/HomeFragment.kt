package com.example.moviesearch.presentation.ui.ui.home

import android.os.Bundle
import android.util.Log
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
import com.example.moviesearch.databinding.FragmentHomeBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import com.example.moviesearch.presentation.ui.adapters.CollectionAdapter
import com.example.moviesearch.presentation.ui.adapters.MovieHomeFromKinopoiskAdapter
import com.example.moviesearch.presentation.ui.serialPage.SerialPageViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return HomeViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
                viewModel.loadCollection(1,
                    "Любимые",
                    0,
                    "R.drawable.baseline_person_collection")
                viewModel.loadCollection(
                    2,
                    "Хочу посмотреть",
                    0,
                    "R.drawable.baseline_person_collection"
                )
        }
        lifecycleScope.launch {
            val getListMovieTop = viewModel.getListMovieTop()
            val collectionTypeTop = "Популярное"
            val mutableListFilmTop =
                viewModel.get15ItemsInTheMovieList(getListMovieTop, collectionTypeTop)
            val movieHomeAdapterTop = MovieHomeFromKinopoiskAdapter(mutableListFilmTop) {
                onItemClickMovie(it)
            }
            binding.recyclerViewTop.adapter = movieHomeAdapterTop

            val getListMovieTop250 = viewModel.getListMovieTop250()
            val collectionTypeTop250 = "Топ-250"
            val mutableListFilmTop250 =
                viewModel.get15ItemsInTheMovieList(getListMovieTop250, collectionTypeTop250)
            val movieHomeAdapterTop250 = MovieHomeFromKinopoiskAdapter(mutableListFilmTop250) {
                onItemClickMovie(it)
            }
            binding.recyclerViewTop250.adapter = movieHomeAdapterTop250

            val getListMoviePrimeres = viewModel.getListMoviePrimeres()
            val collectionTypePrimeres = "Премьеры"
            val mutableListFilmPrimeres =
                viewModel.get15ItemsInTheMovieList(getListMoviePrimeres, collectionTypePrimeres)
            val movieHomeAdapterPrimeres = MovieHomeFromKinopoiskAdapter(mutableListFilmPrimeres) {
                onItemClickMovie(it)
            }
            binding.recyclerViewPremieres.adapter = movieHomeAdapterPrimeres

            val getListMovieUSMilitants = viewModel.getListMovieUSMilitants()
            val collectionTypeUSMilitants = "Боевики США"
            val mutableListFilmUSMilitants = viewModel.get15ItemsInTheMovieList(
                getListMovieUSMilitants,
                collectionTypeUSMilitants
            )
            val movieHomeAdapterUSMilitants =
                MovieHomeFromKinopoiskAdapter(mutableListFilmUSMilitants) {
                    onItemClickMovie(it)
                }
            binding.recyclerViewUSmilitants.adapter = movieHomeAdapterUSMilitants

            val getListMovieDramasOfFrance = viewModel.getListMovieDramasOfFrance()
            val collectionTypeDramasOfFrance = "Драмы Франции"
            val mutableListFilmDramasOfFrance = viewModel.get15ItemsInTheMovieList(
                getListMovieDramasOfFrance,
                collectionTypeDramasOfFrance
            )
            val movieHomeAdapterDramasOfFrance =
                MovieHomeFromKinopoiskAdapter(mutableListFilmDramasOfFrance) {
                    onItemClickMovie(it)
                }
            binding.recyclerViewDramasOfFrance.adapter = movieHomeAdapterDramasOfFrance

            val getListMovieSeries = viewModel.getListMovieSeries()
            val collectionTypeSeries = "Сериалы"
            val mutableListFilmSeries =
                viewModel.get15ItemsInTheMovieList(getListMovieSeries, collectionTypeSeries)
            val movieHomeAdapterSeries = MovieHomeFromKinopoiskAdapter(mutableListFilmSeries) {
                onItemClickSerias(it)
            }
            binding.recyclerViewSerials.adapter = movieHomeAdapterSeries
        }
        binding.buttonAllPremieres.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("CollectionType", binding.textPremieres.text.toString())
            findNavController().navigate(
                R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        }
        binding.buttonAllTop.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("CollectionType", binding.textTop.text.toString())
            findNavController().navigate(
                com.example.moviesearch.R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        }
        binding.buttonAllSerials.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("CollectionType", binding.textSerials.text.toString())
            findNavController().navigate(
                R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        }
        binding.buttonAllUSmilitants.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("CollectionType", binding.textUSmilitants.text.toString())
            findNavController().navigate(
                R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        }
        binding.buttonAllDramasOfFrance.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("CollectionType", binding.textDramasOfFrance.text.toString())
            findNavController().navigate(
                R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        }
        binding.buttonAllTop250.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("CollectionType", binding.textTop250.text.toString())
            findNavController().navigate(
                R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        }
    }

    private fun onItemClickMovie(item: MovieFromKinopoisk) {
        if (item.posterUrlPreview == "1") {
            val bundle = Bundle()
            bundle.putString("CollectionType", item.description)
            findNavController().navigate(
                R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        } else {
            val bundle = Bundle().apply {
                putInt("filmId", item.kinopoiskId)
            }
            findNavController().navigate(R.id.action_navigation_home_to_moviePageFragment, bundle)
        }
    }

    private fun onItemClickSerias(item: MovieFromKinopoisk) {
        if (item.posterUrlPreview == "1") {
            val bundle = Bundle()
            bundle.putString("CollectionType", item.description)
            findNavController().navigate(
                R.id.action_navigation_home_to_listContentFragment,
                bundle
            )
        } else {
            val bundle = Bundle().apply {
                putInt("filmId", item.kinopoiskId)
                putString("nameSerial", item.nameRu)
            }
            findNavController().navigate(R.id.action_navigation_home_to_serialPageFragment, bundle)
        }
    }
}