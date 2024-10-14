package com.example.moviesearch.presentation.ui.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.databinding.FragmentSearchBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.foundMovies.FoundMovie
import com.example.moviesearch.presentation.ui.adapters.FoundMoviesAdapter
import com.example.moviesearch.presentation.ui.serialPage.SerialPageViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

class SearchFragment : Fragment() {
    private var contries = 1
    private var genres = 1
    private var order = "RATING"
    private var type = "ALL"
    private var ratingFrom = 0
    private var ratingTo = 10
    private var yearFrom = 1000
    private var yearTo = 3000
    private var tupeSort = "YEAR"
    private var tupeFilm = "ALL"
    private var keyword = ""
    private var viewedMovieOrNot = "ViewedOn"

    private val viewModel: SearchViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return SearchViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentSearchBinding? = null

    private val binding: FragmentSearchBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contries = it.getInt("contries")
            genres = it.getInt("genres")
            order = it.getString("order").toString()
            type = it.getString("type").toString()
            ratingFrom = it.getInt("ratingFrom")
            ratingTo = it.getInt("ratingTo")
            yearFrom = it.getInt("yearFrom")
            yearTo = it.getInt("yearTo")
            tupeSort = it.getString("tupeSort").toString()
            tupeFilm = it.getString("tupeFilm").toString()
            viewedMovieOrNot = it.getString("viewedMovieOrNot").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater)
        lifecycleScope.launch {
            val getInformationPersonFilm = viewModel.getInformationPersonFilm(
                contries,
                genres,
                order,
                type,
                ratingFrom,
                ratingTo,
                yearFrom,
                yearTo,
                keyword
            )
            val FoundMoviesAdapter = FoundMoviesAdapter(getInformationPersonFilm)
            binding.recyclerViewFoundMovie.adapter = FoundMoviesAdapter
        }
        val editText = binding.searchEditText.editText
        if (editText != null) {
            editText.doOnTextChanged { text, _, _, _ ->
                lifecycleScope.launch {
                    keyword = text.toString()
                    val getInformationPersonFilm = viewModel.getInformationPersonFilm(
                        contries,
                        genres,
                        order,
                        type,
                        ratingFrom,
                        ratingTo,
                        yearFrom,
                        yearTo,
                        keyword
                    )
                    if (viewedMovieOrNot == "ViewedOff") {
                        var sortList : List<FoundMovie>
                        viewModel.getAllViewedMovie().collect {
                             sortList =
                                viewModel.getListWithoutViewedMovie(it, getInformationPersonFilm)
                            val FoundMoviesAdapter = FoundMoviesAdapter(sortList)
                            binding.recyclerViewFoundMovie.adapter = FoundMoviesAdapter
                        }
                    } else {
                        val FoundMoviesAdapter = FoundMoviesAdapter(getInformationPersonFilm)
                        binding.recyclerViewFoundMovie.adapter = FoundMoviesAdapter
                    }
                }
            }
        }
        val bundle = Bundle().apply {
            putInt("contries", contries)
            putInt("genres", genres)
            putString("order", order)
            putString("type", type)
            putInt("ratingFrom", ratingFrom)
            putInt("ratingTo", ratingTo)
            putInt("yearFrom", yearFrom)
            putInt("yearTo", yearTo)
            putString("tupeSort", tupeSort)
            putString("tupeFilm", tupeFilm)
        }
        binding.buttonToFilterSearch.setOnClickListener {
            findNavController().navigate(
                com.example.moviesearch.R.id.action_navigation_search_to_filterSearchFragment,
                bundle
            )
        }
        return binding.root
    }

}