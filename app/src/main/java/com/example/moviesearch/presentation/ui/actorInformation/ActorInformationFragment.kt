package com.example.moviesearch.presentation.ui.actorInformation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.databinding.FragmentActorInformationBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import com.example.moviesearch.entity.informationPerson.Movie
import com.example.moviesearch.presentation.ui.adapters.BestFilmActorAdapter
import com.example.moviesearch.presentation.ui.adapters.FilmographyAdapter
import com.example.moviesearch.presentation.ui.moviePage.MoviePageViewModel
import kotlinx.coroutines.launch

class ActorInformationFragment : Fragment() {
    private var staffId: Int? = 111
    private var filmId: Int? = 535341
    private val listUriPhoto: List<String> = listOf()
    private var _binding: FragmentActorInformationBinding? = null

    private val viewModel: ActorInformationViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return ActorInformationViewModel(wordsDao) as T
            }
        }
    }

    private val binding: FragmentActorInformationBinding
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
        _binding = FragmentActorInformationBinding.inflate(inflater)
        lifecycleScope.launch {
            val getInformationPerson =
                staffId?.let { viewModel.getInformationPerson(it) }
            if (getInformationPerson != null) {
                Glide
                    .with(binding.imageViewPhotoActor.context)
                    .load(getInformationPerson.posterUrl)
                    .into(binding.imageViewPhotoActor)
                binding.textViewNameActor.text = getInformationPerson.nameRu
                binding.textViewProfession.text = getInformationPerson.profession
                binding.textViewNumberOfMovie.text = "${getInformationPerson.films.size} фильма"

            }

            val getInformationPersonFilm =
                staffId?.let { viewModel.getInformationPersonFilm(it) }
            var getInformationPersonFilm15Values = listOf<Movie>()
            if (getInformationPersonFilm != null) {
                getInformationPersonFilm15Values =
                    viewModel.getAlistWith15Values(getInformationPersonFilm)
            }
            val getlistUriforGlige =
                getInformationPersonFilm15Values?.let { viewModel.getListUriPhoto(it) }
            if (getlistUriforGlige != null) {
                val ActorAdapter = getInformationPersonFilm?.let {
                    BestFilmActorAdapter(
                        it,
                        getlistUriforGlige
                    ) { onItemClick(it) }
                }
                binding.recyclerViewActorFilm.adapter = ActorAdapter
            }

        }
        binding.buttonAllActorMovie.setOnClickListener {
            val bundle = Bundle().apply {
                staffId?.let { it1 -> putInt("staffId", it1) }
                filmId?.let { it2 -> putInt("filmId", it2) }
            }
            findNavController().navigate(
                R.id.action_actorInformationFragment_to_filmographyFragment,
                bundle
            )
        }
        binding.buttonBack.setOnClickListener {
            lifecycleScope.launch {
                val filmInformation =
                    filmId?.let { viewModel.filmInformation(it) }
                if (filmInformation != null) {
                    val bundle = Bundle().apply {
                        filmId?.let { it1 -> putInt("filmId", it1) }
                    }
                    findNavController().navigate(
                        R.id.action_actorInformationFragment_to_moviePageFragment,
                        bundle
                    )
                }
            }
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