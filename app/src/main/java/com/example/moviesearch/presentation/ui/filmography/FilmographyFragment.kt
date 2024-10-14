package com.example.moviesearch.presentation.ui.filmography

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
import com.example.moviesearch.databinding.FragmentFilmographyBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.informationPerson.Movie
import com.example.moviesearch.presentation.ui.adapters.FilmographyAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch

class FilmographyFragment : Fragment() {
    private var staffId: Int? = null
    private var filmId: Int? = 535341
    private var _binding: FragmentFilmographyBinding? = null

    private val viewModel: FilmographyViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return FilmographyViewModel(wordsDao) as T
            }
        }
    }

    private val binding: FragmentFilmographyBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            staffId = it.getInt("staffId")
            filmId = it.getInt("filmId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmographyBinding.inflate(inflater)

        lifecycleScope.launch {
            val getInformationPerson =
                staffId?.let { viewModel.getInformationPerson(it) }
            if (getInformationPerson != null) {
                binding.textViewNameActor.text = getInformationPerson.nameRu
            }
            binding.textHeading
            val getInformationPersonFilm =
                staffId?.let { viewModel.getInformationPersonFilm(it) }
            val getlistUriforGlige =
                getInformationPersonFilm?.let { viewModel.getListUriPhoto(it) }
            if (getlistUriforGlige != null) {
                val ActorAdapter = getInformationPersonFilm?.let { FilmographyAdapter(it,getlistUriforGlige ){ onItemClick(it)} }
                binding.recyclerFilmography.adapter = ActorAdapter
            }
        }
        binding.buttonBack.setOnClickListener {
            val bundle = Bundle().apply{
                staffId?.let { it1 -> putInt("staffId", it1) }
                filmId?.let{it2 -> putInt("filmId", it2)}
            }
            findNavController().navigate(R.id.action_filmographyFragment_to_actorInformationFragment, bundle)
        }
        return binding.root
    }
    private fun onItemClick(item: Movie) {
        val bundle = Bundle().apply {
            filmId?.let { putInt("filmId", item.filmId) }
            staffId?.let { putInt("staffId", staffId!!) }
        }
        findNavController().navigate(
            R.id.action_actorInformationFragment_to_moviePageFragment,
            bundle
        )
    }
}