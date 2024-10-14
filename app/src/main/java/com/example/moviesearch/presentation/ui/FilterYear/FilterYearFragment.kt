package com.example.moviesearch.presentation.ui.FilterYear

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.databinding.FragmentFilterYearBinding

import com.example.moviesearch.presentation.ui.adapters.ButtonListYearFromAdapter
import com.example.moviesearch.presentation.ui.adapters.ButtonListYearToAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel

class FilterYearFragment : Fragment() {
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
    private var yearList = mutableListOf<Int>()

    private val viewModel: FilterYearViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return FilterYearViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentFilterYearBinding? = null

    private val binding: FragmentFilterYearBinding
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
        _binding = FragmentFilterYearBinding.inflate(inflater)
        yearList = viewModel.getListYearForFilter()
        binding.recyclerViewGetYearFrom.adapter  = ButtonListYearFromAdapter(yearList) { onItemClickFrom(it) }
        binding.recyclerViewGetYearTo.adapter = ButtonListYearToAdapter(yearList) { onItemClickTo(it) }

        binding.buttonBack.setOnClickListener {
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
            }
            findNavController().navigate(
                R.id.action_filterYearFragment_to_filterSearchFragment,
                bundle
            )
        }
        binding.buttonHoosingTheYear.setOnClickListener {
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
            findNavController().navigate(
                R.id.action_filterYearFragment_to_filterSearchFragment,
                bundle
            )
        }


        return binding.root
    }

    private fun onItemClickFrom(item: Int) {
        yearFrom = item
    }

    private fun onItemClickTo(item: Int) {
        if (item < yearFrom) {
            yearTo = yearFrom
        } else {
            yearTo = item
        }
    }
}

