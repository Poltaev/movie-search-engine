package com.example.moviesearch.presentation.ui.contentFromTheProfile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.dataBase.FilmForCollection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.databinding.FragmentContentFromTheProfileBinding
import com.example.moviesearch.databinding.FragmentListContentBinding
import com.example.moviesearch.presentation.ui.adapters.FilmFromCollectionMovieAdapter
import com.example.moviesearch.presentation.ui.adapters.HistoryActorMovieAdapter
import com.example.moviesearch.presentation.ui.adapters.ViewedMovieAdapter
import com.example.moviesearch.presentation.ui.listContent.ListContentViewModel
import kotlinx.coroutines.launch


class ContentFromTheProfileFragment : Fragment() {

    private var typeCollection: String? = "String"
    private var checkCollection: Int = 0

    private val viewModel: ContentFromTheProfileViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return ContentFromTheProfileViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentContentFromTheProfileBinding? = null

    private val binding: FragmentContentFromTheProfileBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeCollection = it.getString("typeCollection")
            checkCollection = it.getInt("checkCollection")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentFromTheProfileBinding.inflate(inflater)
        binding.buttonBackContentFragmentToProfile.setOnClickListener {
            findNavController().navigate(R.id.action_contentFromTheProfileFragment_to_navigation_profile)
        }
        if (checkCollection == 0) {
            binding.textHeadingContent.text = typeCollection
        } else {
            lifecycleScope.launch {
                viewModel.getCollection().collect {
                    it.forEach {
                        if (it.id == checkCollection) {
                            binding.textHeadingContent.text = it.nameCollection
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            if (checkCollection == 0) {
                if (typeCollection == "Просмотренно") {
                    launch {
                        viewModel.getAllViewedMovie().collect {
                            val movieViewedAdapter =
                                ViewedMovieAdapter(it) { onItemClickViewed(it) }
                            Log.i("DataBaseViewed", "${it.size}")
                            binding.recyclerViewCollectionContent.adapter = movieViewedAdapter
                        }
                    }
                } else {
                    launch {
                        viewModel.getHistoryMovie().collect {
                            val movieHomeAdapterSeries =
                                HistoryActorMovieAdapter(it) { onItemClickHistoryMovieActor(it) }
                            Log.i("DataBaseHistory", "${it.size}")
                            binding.recyclerViewCollectionContent.adapter = movieHomeAdapterSeries
                        }
                    }
                }
            } else {
                viewModel.getAllCollectionMovie().collect {
                    val listFilmFromCollection = mutableListOf<FilmForCollection>()
                    val filterCollection = it.filter { it.collectionId == checkCollection }
                    val mutableLIstFilmInThisCollection = mutableListOf<Int>()
                    filterCollection.forEach {
                        mutableLIstFilmInThisCollection.add(it.filmId)
                    }
                    viewModel.getAllSavedFilm().collect {
                        it.forEach { dataBase ->
                            mutableLIstFilmInThisCollection.forEach {
                                if (dataBase.filmId == it) {
                                    listFilmFromCollection.add(dataBase)
                                }
                            }
                        }
                        val movieCollectAdapterSeries =
                            FilmFromCollectionMovieAdapter(listFilmFromCollection) {
                                onItemClickCollection(
                                    it
                                )
                            }
                        Log.i("DataBaseHistory", "${it.size}")
                        binding.recyclerViewCollectionContent.adapter = movieCollectAdapterSeries
                    }

                }
            }
        }


        return binding.root
    }

    private fun onItemClickViewed(item: ViewedMovie) {
        val filmId = item.filmId
        val bundle = Bundle().apply {
            putInt("filmId", filmId)
        }
        findNavController().navigate(
            R.id.action_contentFromTheProfileFragment_to_moviePageFragment,
            bundle
        )
    }

    private fun onItemClickHistoryMovieActor(item: HistoryMovieActor) {
        val filmId = item.filmIdActor
        val bundle = Bundle().apply {
            putInt("filmId", filmId)
        }
        findNavController().navigate(
            R.id.action_contentFromTheProfileFragment_to_moviePageFragment,
            bundle
        )
    }

    private fun onItemClickCollection(item: FilmForCollection) {
        val filmId = item.filmId
        val bundle = Bundle().apply {
            putInt("filmId", filmId)
        }
        findNavController().navigate(
            R.id.action_contentFromTheProfileFragment_to_moviePageFragment,
            bundle
        )
    }
}
