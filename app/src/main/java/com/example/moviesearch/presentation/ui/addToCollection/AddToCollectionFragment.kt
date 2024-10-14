package com.example.moviesearch.presentation.ui.addToCollection

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
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.databinding.FragmentAddToCollectionBinding
import com.example.moviesearch.databinding.FragmentSetCollectionBinding
import com.example.moviesearch.presentation.ui.adapters.AddCollectionAdapter
import com.example.moviesearch.presentation.ui.adapters.CollectionAdapter
import com.example.moviesearch.presentation.ui.setCollection.SetCollectionViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

class AddToCollectionFragment : Fragment() {
    private var filmId: Int? = 535341
    private var posterUrl: String? = "poster"
    private var ratingKinopoisk: String? = "rating"
    private var nameRu: String? = "name"
    private var genre: String? = "genre"

    private val viewModel: AddToCollectionViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return AddToCollectionViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentAddToCollectionBinding? = null

    private val binding: FragmentAddToCollectionBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmId = it.getInt("filmId")
            posterUrl = it.getString("posterUrl")
            ratingKinopoisk = it.getString("ratingKinopoisk")
            nameRu = it.getString("nameRu")
            genre = it.getString("genre")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddToCollectionBinding.inflate(inflater)

        binding.imageButtonBack.setOnClickListener {
            val bundle = Bundle().apply {
                filmId?.let { putInt("filmId", it) }
            }
            findNavController().navigate(
                R.id.action_addToCollectionFragment_to_moviePageFragment,
                bundle
            )
        }

        Glide
            .with(binding.movieImageView.context)
            .load(posterUrl)
            .into(binding.movieImageView)
        binding.topKinopoisk.text = ratingKinopoisk.toString()
        binding.textNameMovie.text = nameRu.toString()
        binding.textGanreMovie.text = genre.toString()

        lifecycleScope.launch {
            viewModel.getCollection().collect {
                val addCollectionAdapter =
                    AddCollectionAdapter(it) { onItemClickCollection(it) }

                binding.recyclerViewCollection.adapter = addCollectionAdapter
            }
        }

        binding.buttonMakeCollections.setOnClickListener {
            val bundle = Bundle().apply {
                filmId?.let { putInt("filmId", it) }
                posterUrl?.let { putString("posterUrl", it) }
                ratingKinopoisk?.let { putString("ratingKinopoisk", it) }
                nameRu?.let { putString("nameRu", it) }
                genre?.let { putString("genre", it) }
            }
            findNavController().navigate(R.id.action_addToCollectionFragment_to_createCollectionFragment, bundle)
        }
        return binding.root
    }

    private fun onItemClickCollection(item: Collection) {
        filmId?.let {
            viewModel.loadInCollectionMovie(
                item.id,
                it
            )
        }

    }
}