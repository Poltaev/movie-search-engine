package com.example.moviesearch.presentation.ui.filterSearch

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
import com.example.moviesearch.databinding.FragmentFilterSearchBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch

class FilterSearchFragment : Fragment() {
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
    private val listTypeSort = listOf("RATING", "NUM_VOTE", "YEAR")
    private val listTypeFilm = listOf("ALL", "FILM", "MINI_SERIES")
    private var viewedMovieOrNot = "ViewedOn"

    private val viewModel: FilterSearchViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return FilterSearchViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentFilterSearchBinding? = null

    private val binding: FragmentFilterSearchBinding
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
        _binding = FragmentFilterSearchBinding.inflate(inflater)
        lifecycleScope.launch {
            binding.buttonCountryFilter.text = viewModel.getContries(contries)
            binding.buttonGanreFilter.text = viewModel.getGenre(genres)
            binding.buttonFilterYear.text = "C ${yearFrom} до ${yearTo}"
            binding.textViewSetRating.text = "C ${ratingFrom} до ${ratingTo}"
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
            putString("viewedMovieOrNot", viewedMovieOrNot)

        }
        binding.buttonCountryFilter.setOnClickListener {
            findNavController().navigate(R.id.action_filterSearchFragment_to_countryFilterFragment, bundle)
        }
        binding.buttonGanreFilter.setOnClickListener {
            findNavController().navigate(R.id.action_filterSearchFragment_to_genreFilterFragment, bundle)
        }
        binding.buttonFilterYear.setOnClickListener {
            findNavController().navigate(R.id.action_filterSearchFragment_to_filterYearFragment, bundle)
        }
        binding.radioGroupSort.setOnCheckedChangeListener{ _, buttnId ->
            when (buttnId) {
                R.id.radioButtonData -> {tupeSort = listTypeSort[2]}
                R.id.radioButtonTop -> {tupeSort = listTypeSort[1]}
                R.id.radioButtonRating -> {tupeSort = listTypeSort[0]}
            }
        }
        binding.radioButtonData.isChecked = true
        binding.radioGroupTypeFilm.setOnCheckedChangeListener{ _, buttnId ->
            when (buttnId) {
                R.id.radioButtonAll -> {tupeFilm = listTypeFilm[0]}
                R.id.radioButtonMovie -> {tupeFilm = listTypeFilm[1]}
                R.id.radioButtonSerial -> {tupeFilm = listTypeFilm[2]}
            }
        }
        binding.radioButtonAll.isChecked = true
        binding.rangeSeekBar.addOnChangeListener { slider, value, fromUser ->
            ratingFrom = slider.values[0].toInt()
            if (ratingFrom > slider.values[1].toInt()){
                ratingTo = ratingFrom
            } else {
                ratingTo = slider.values[1].toInt()
            }
            binding.textViewSetRating.text = "От ${ratingFrom} до ${ratingTo} "
        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_filterSearchFragment_to_navigation_search, bundle)
        }
        binding.buttonVewingStatus.setOnClickListener {
            if (binding.buttonVewingStatus.text == "Не просмотрено"){
                binding.buttonVewingStatus.text = "Просмотрено"
                viewedMovieOrNot = "ViewedOn"
                binding.imageViewVisibility.setImageResource(R.drawable.baseline_visibility_for_filter)
            }else{
                binding.buttonVewingStatus.text = "Не просмотрено"
                viewedMovieOrNot = "ViewedOff"
                binding.imageViewVisibility.setImageResource(R.drawable.baseline_visibility_off_for_filter)
            }
        }
        return binding.root
    }


}
