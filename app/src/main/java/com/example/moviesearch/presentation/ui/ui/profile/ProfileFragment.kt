package com.example.moviesearch.presentation.ui.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.databinding.FragmentProfileBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import com.example.moviesearch.presentation.ui.adapters.CollectionAdapter
import com.example.moviesearch.presentation.ui.adapters.HistoryActorMovieAdapter
import com.example.moviesearch.presentation.ui.adapters.MovieHomeFromKinopoiskAdapter
import com.example.moviesearch.presentation.ui.adapters.ViewedMovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var typeCollection: String = ""
    private var checkCollection: Int = 0

    private val viewModel: ProfileViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return ProfileViewModel(wordsDao) as T
            }
        }
    }
    private var _binding: FragmentProfileBinding? = null

    private val binding: FragmentProfileBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeCollection = it.getString("typeCollection").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)
        lifecycleScope.launch {
            viewModel.getCollection().collect {
                val collectionAdapter =
                    CollectionAdapter(it) { onItemClickCollection(it) }
                Log.i("DataBaseViewed", "${it.size}")
                binding.recyclerViewCollections.adapter = collectionAdapter
            }
        }
        binding.buttonAllViewed.setOnClickListener {
            typeCollection = "Просмотренно"
            val bundle = Bundle().apply {
                putString("typeCollection", typeCollection)
            }
            findNavController().navigate(
                R.id.action_navigation_profile_to_contentFromTheProfileFragment,
                bundle
            )
        }
        binding.buttonAllHistory.setOnClickListener {
            typeCollection = "Вам было интересно"
            val bundle = Bundle().apply {
                putString("typeCollection", typeCollection)
            }
            findNavController().navigate(
                R.id.action_navigation_profile_to_contentFromTheProfileFragment,
                bundle
            )
        }
        lifecycleScope.launch {
            launch {
                viewModel.getAllViewedMovie().collect {
                    val viewedListFilm = viewModel.get15ItemsInTheMovieViewedList(it)
                    val movieViewedAdapter =
                        ViewedMovieAdapter(viewedListFilm) { onItemClickViewed(it) }
                    Log.i("DataBaseViewed", "${it.size}")
                    binding.recyclerViewViewed.adapter = movieViewedAdapter
                }
            }
            launch {
                viewModel.getHistoryMovie().collect {
                    val historyListFilm = viewModel.get15ItemsInTheMovieViewedHistoryList(it)
                    val movieHomeAdapterSeries =
                        HistoryActorMovieAdapter(historyListFilm) { onItemClickHistoryMovieActor(it) }
                    Log.i("DataBaseHistory", "${it.size}")
                    binding.recyclerViewHistory.adapter = movieHomeAdapterSeries
                }
            }
        }
        binding.buttonMakeCollections.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_setCollectionFragment)
        }
        return binding.root
    }

    private fun onItemClickViewed(item: ViewedMovie) {
        if (item.nameMovie == "11111") {
            viewModel.deleteAllViewedMovie()
        }
    }

    private fun onItemClickHistoryMovieActor(item: HistoryMovieActor) {
        if (item.nameMovieActor == "11111") {
            viewModel.deleteAllActorMovie()
        }
    }

    private fun onItemClickCollection(item: Collection) {
        checkCollection = item.id
        val bundle = Bundle().apply {
            putInt("checkCollection", checkCollection)
        }
        findNavController().navigate(
            R.id.action_navigation_profile_to_contentFromTheProfileFragment,
            bundle
        )
    }

}