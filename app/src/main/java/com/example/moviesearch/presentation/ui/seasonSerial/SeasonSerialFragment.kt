package com.example.moviesearch.presentation.ui.seasonSerial

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
import com.example.moviesearch.databinding.FragmentSeasonSerialBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.SerialSeries.InformationSerialSeries
import com.example.moviesearch.presentation.ui.adapters.SeasonButtonAdapter
import com.example.moviesearch.presentation.ui.adapters.SerialSeriesAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch

class SeasonSerialFragment : Fragment() {
    private var filmId: Int? = 535341
    private var nameSerial: String? = "535341"
    private val listNumberSeasonButton =
        mutableListOf("1 сезон")

    private val viewModel: SeasonSerialViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return SeasonSerialViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentSeasonSerialBinding? = null


    private val binding: FragmentSeasonSerialBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmId = it.getInt("filmId")
            nameSerial = it.getString("nameSerial")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSeasonSerialBinding.inflate(inflater)
        binding.textNameSerial.text = nameSerial
        binding.buttonBack.setOnClickListener {
            val bundle = Bundle().apply {
                filmId?.let { it1 -> putInt("filmId", it1) }
            }
            findNavController().navigate(
                R.id.action_seasonSerialFragment_to_serialPageFragment,
                bundle
            )
        }
        lifecycleScope.launch {
            val getListSerialSeason =
                filmId?.let { viewModel.getListSerialSeason(it) }
            val thisSeason = getListSerialSeason?.items?.get(0)?.episodes
            val SeasonAdapter = thisSeason?.let { SerialSeriesAdapter(it) }
            binding.recyclerViewNumberSeries.adapter = SeasonAdapter
        }
        lifecycleScope.launch {
            val getListSerialSeason =
                filmId?.let { viewModel.getListSerialSeason(it) }
            var numberSeries = getListSerialSeason?.total
            if (numberSeries!! >= 2) {
                for (i in 2..numberSeries!!) {
                    listNumberSeasonButton.add("${i} сезон")
                }
            }
            binding.recyclerViewButtonSeason.adapter =
                listNumberSeasonButton.let {
                    SeasonButtonAdapter(listNumberSeasonButton) {
                        if (getListSerialSeason != null) {
                            onItemClick(
                                it, getListSerialSeason
                            )
                        }
                    }
                }
        }
        return binding.root
    }

    private fun onItemClick(item: String, getListSerialSeason: InformationSerialSeries) {
        Log.i("Number", "numberSeason ")
        lifecycleScope.launch {
            Log.i("Number", "numberSeason ")
            item.toList()
            listNumberSeasonButton.forEach {
                Log.i("Number", "${it} ${item} ")
                if (it == item) {
                    var numberSeason = listNumberSeasonButton.indexOf(it)
                    Log.i("Number", "numberSeason ${numberSeason}")
                    val thisSeason = getListSerialSeason?.items?.get(numberSeason)?.episodes
                    var getNumberSizeSeries = thisSeason?.size
                    binding.textSeasonSeries.text = "${numberSeason + 1 } сезон, ${getNumberSizeSeries} серий"
                    val SeasonAdapter = thisSeason?.let { SerialSeriesAdapter(it) }
                    binding.recyclerViewNumberSeries.adapter = SeasonAdapter
                }
            }

        }
    }
}