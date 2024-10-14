package com.example.moviesearch.presentation.ui.countryFilter

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
import com.example.moviesearch.databinding.FragmentCountryFilterBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listContryGenre.IdCountry
import com.example.moviesearch.presentation.ui.adapters.ButtonListCountryAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch

class CountryFilterFragment : Fragment() {
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

    private val viewModel: CountryFilterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return CountryFilterViewModel(wordsDao) as T
            }
        }
    }
    private var _binding: FragmentCountryFilterBinding? = null
    private val binding: FragmentCountryFilterBinding
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
        _binding = FragmentCountryFilterBinding.inflate(inflater)
        lifecycleScope.launch {
            val getListCountry = viewModel.getListCountry()
            val movieHomeAdapterTop = ButtonListCountryAdapter(getListCountry){ onItemClick(it)}
            binding.recyclerViewGetCountry.adapter = movieHomeAdapterTop
        }
        binding.buttonBack.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("contries", contries)
            }
            findNavController().navigate(R.id.action_countryFilterFragment_to_filterSearchFragment, bundle)
        }
        return binding.root
    }
    private  fun onItemClick(item : IdCountry)  {
        val bundle = Bundle().apply {
            putInt("contries", item.id)
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
        findNavController().navigate(R.id.action_countryFilterFragment_to_filterSearchFragment, bundle)
    }
}