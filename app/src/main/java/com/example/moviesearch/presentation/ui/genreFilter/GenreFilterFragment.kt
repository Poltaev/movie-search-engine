package com.example.moviesearch.presentation.ui.genreFilter

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
import com.example.moviesearch.databinding.FragmentGenreFilterBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listContryGenre.IdGenre
import com.example.moviesearch.presentation.ui.adapters.ButtonListGenreAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch


class GenreFilterFragment : Fragment() {
    private var contries = 1
    private var genres = 1
    private var order = "RATING"
    private var type = "ALL"
    private var ratingFrom = 0
    private var ratingTo = 10
    private var yearFrom = 1000
    private var yearTo = 3000
    private var tupeSort = ""
    private var tupeFilm = "ALL"

    private val viewModel: GenreFilterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return GenreFilterViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentGenreFilterBinding? = null

    private val binding: FragmentGenreFilterBinding
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

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenreFilterBinding.inflate(inflater)
        lifecycleScope.launch {
            val getListGenre = viewModel.getListGenre()
            val movieHomeAdapterTop = ButtonListGenreAdapter(getListGenre){ onItemClick(it)}
            binding.recyclerViewGetGenre.adapter = movieHomeAdapterTop
        }
        binding.buttonBack.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("genres", genres)
            }
            findNavController().navigate(R.id.action_genreFilterFragment_to_filterSearchFragment, bundle)
        }
        return binding.root
    }
    private  fun onItemClick(item : IdGenre)  {
        val bundle = Bundle().apply {
            putInt("contries", contries)
            putInt("genres", item.id)
            putString("order", order)
            putString("type", type)
            putInt("ratingFrom", ratingFrom)
            putInt("ratingTo", ratingTo)
            putInt("yearFrom", yearFrom)
            putInt("yearTo", yearTo)
            putString("tupeSort", tupeSort)
            putString("tupeFilm", tupeFilm)
        }
        findNavController().navigate(R.id.action_genreFilterFragment_to_filterSearchFragment, bundle)
    }
}