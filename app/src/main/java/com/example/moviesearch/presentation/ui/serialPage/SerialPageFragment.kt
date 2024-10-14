package com.example.moviesearch.presentation.ui.serialPage

import android.content.Intent
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

import com.example.moviesearch.databinding.FragmentSerialPageBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import com.example.moviesearch.entity.filmIdInformation.Countries
import com.example.moviesearch.entity.filmIdInformation.Genres
import com.example.moviesearch.entity.similars.SimilarMovie
import com.example.moviesearch.presentation.GalleryAdapter
import com.example.moviesearch.presentation.ui.adapters.SimilarMovieAdapter
import com.example.moviesearch.presentation.ui.adapters.ActorDirectorAdapter
import com.example.moviesearch.presentation.ui.ui.profile.ProfileViewModel
import kotlinx.coroutines.launch

class SerialPageFragment : Fragment() {
    private var filmId: Int? = 535341
    private var theMovieHasBeenViewed = false
    private var theMovieHasBeenLove = false
    private var theMovieHasBeenWant = false
    private var posterUrl: String? = "poster"
    private var ratingKinopoisk: String? = "rating"
    private var nameRu: String? = "name"
    private var genre: String? = "genre"
    private var nameSerial : String? = "535341"
    private val viewModel: SerialPageViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return SerialPageViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentSerialPageBinding? = null

    private val binding: FragmentSerialPageBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmId = it.getInt("filmId")
            nameSerial = it.getString("nameSerial")        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSerialPageBinding.inflate(inflater)
        lifecycleScope.launch {
            val getListSerialSries =
                filmId?.let { viewModel.getListSerials(it) }
            binding.textSetSerialSeason.text =
                "В сезоне - ${getListSerialSries?.get(0)?.number.toString()}, серий - ${
                    getListSerialSries?.get(0)?.episodes?.last()?.episodeNumber.toString()
                } "
            val filmInformation =
                filmId?.let { viewModel.getFiilmInformation(it) }

            if (filmInformation != null) {
                posterUrl = filmInformation.posterUrl
                ratingKinopoisk = filmInformation.ratingKinopoisk.toString()
                nameRu = filmInformation.nameRu
                genre = filmInformation.genres[0].genre
                viewModel.loadWantToSeeMovie(
                    filmInformation.kinopoiskId,
                    filmInformation.nameRu,
                    filmInformation.genres[0].genre,
                    filmInformation.ratingKinopoisk,
                    filmInformation.posterUrlPreview
                )
                Glide
                    .with(binding.imageViewPoster.context)
                    .load(filmInformation.posterUrl)
                    .into(binding.imageViewPoster)
                filmId?.let {
                    viewModel.loadActorMovie(
                        it,
                        filmInformation.nameRu,
                        filmInformation.genres[0].genre,
                        filmInformation.posterUrl
                    )
                }
                binding.buttonSaw.setOnClickListener {
                    lifecycleScope.launch {
                        if (theMovieHasBeenViewed) {
                            binding.buttonSaw.setImageResource(R.drawable.baseline_visibility_to_saw)
                            viewModel.getAllViewedMovie().collect {
                                it.forEach {
                                    if (it.filmId == filmId) {
                                        viewModel.deleteViewedMovie(it)
                                    }
                                }
                            }
                            theMovieHasBeenViewed = false
                        } else {
                            binding.buttonSaw.setImageResource(R.drawable.baseline_visibility_off)
                            filmId?.let {
                                viewModel.loadViewedMovie(
                                    it,
                                    filmInformation.nameRu,
                                    filmInformation.genres[0].genre,
                                    filmInformation.ratingKinopoisk,
                                    filmInformation.posterUrl


                                )
                            }
                            theMovieHasBeenViewed = true
                        }
                    }
                }
                binding.buttonSaw.setOnClickListener {
                    filmId?.let {
                        viewModel.loadViewedMovie(
                            it,
                            filmInformation.nameRu,
                            filmInformation.genres[0].genre,
                            filmInformation.ratingKinopoisk,
                            filmInformation.posterUrl
                        )
                    }
                }
                binding.textViewNameMovie.text = filmInformation.nameRu
                if (getListSerialSries != null) {
                    binding.textViewShortDescription.text =
                        "${filmInformation.ratingKinopoisk}, " +
                                " ${filmInformation.year}, " +
                                "${viewModel.getAllGenre(filmInformation.genres)}" +
                                " ${viewModel.getAllCountry(filmInformation.countries)}" +
                                "${filmInformation.ratingAgeLimits}, " +
                                "${viewModel.translatorOfMinutesIntoHours(filmInformation.filmLength)}"
                }
                binding.textHeadingSerialPage.text = filmInformation.shortDescription
                binding.textInfomationSerialPage.text =
                    viewModel.getShortDescription(filmInformation.description)
            }

            binding.buttonBack.setOnClickListener {
                findNavController().navigate(R.id.action_serialPageFragment_to_navigation_home)
            }
            lifecycleScope.launch {
                val getListActor =
                    filmId?.let { viewModel.getListActor(it) }
                if (getListActor != null) {
                    binding.buttonAllActor.text = getListActor.size.toString()
                }
                val getListDirector =
                    filmId?.let { viewModel.getListDirector(it) }
                if (getListActor != null) {
                    if (getListDirector != null) {
                        binding.buttonAllUDirector.text = getListDirector.size.toString()
                    }
                }
            }
            binding.buttonAllSeason.setOnClickListener {
                val bundle = Bundle().apply {
                    if (filmId != null) {
                        putInt("filmId", filmId!!)
                        putString("nameSerial", nameSerial)
                    }
                }
                findNavController().navigate(
                    R.id.action_serialPageFragment_to_seasonSerialFragment,
                    bundle
                )
            }
            binding.buttonAllGallery.setOnClickListener {
                val bundle = Bundle()
                if (filmId != null) {
                    bundle.putInt("filmId", filmId!!)
                }
                findNavController().navigate(
                    R.id.action_serialPageFragment_to_photoGalleryFragment,
                    bundle
                )
            }
            binding.buttonShare.setOnClickListener {
                val shareText = filmInformation?.webUrl
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here")
                startActivity(Intent.createChooser(shareIntent, "Share text"))
            }

            binding.buttonCollection.setOnClickListener {
                val bundle = Bundle().apply {
                    filmId?.let { putInt("filmId", it) }
                    posterUrl?.let { putString("posterUrl", it) }
                    ratingKinopoisk?.let { putString("ratingKinopoisk", it) }
                    nameRu?.let { putString("nameRu", it) }
                    genre?.let { putString("genre", it) }
                }
                findNavController().navigate(
                    R.id.action_serialPageFragment_to_addToCollectionFragment,
                    bundle
                )
            }
            lifecycleScope.launch {
                viewModel.getAllViewedMovie().collect {
                    it.forEach {
                        if (it.filmId == filmId) {
                            theMovieHasBeenViewed = true
                            binding.buttonSaw.setImageResource(R.drawable.baseline_visibility_off)
                        }
                    }
                }

                viewModel.getAllCollectionMovie().collect {
                    val filterColection = it.filter { it.collectionId == 1 }
                    filterColection.forEach {
                        if (it.filmId == filmId) {
                            theMovieHasBeenLove = true
                            binding.buttonInLove.setImageResource(R.drawable.baseline_love_already)
                        }
                    }
                }
                viewModel.getAllCollectionMovie().collect {
                    val filterColection = it.filter { it.collectionId == 2 }
                    filterColection.forEach {
                        if (it.filmId == filmId) {
                            theMovieHasBeenWant = true
                            binding.buttonWant.setImageResource(R.drawable.baseline_bookmark_want_to_see_it_already)
                        }
                    }
                }
            }

            binding.buttonInLove.setOnClickListener {
                lifecycleScope.launch {
                    if (theMovieHasBeenLove) {
                        binding.buttonInLove.setImageResource(R.drawable.baseline_button_drawable_love)
                        viewModel.getAllCollectionMovie().collect {
                            val filterColection = it.filter { it.collectionId == 1 }
                            filterColection.forEach {
                                if (it.filmId == filmId) {
                                    viewModel.deleteCollectionMovie(it)
                                }
                            }
                        }
                        theMovieHasBeenLove = false
                    } else {
                        binding.buttonInLove.setImageResource(R.drawable.baseline_love_already)
                        filmId?.let {
                            viewModel.loadInCollectionMovie(
                                1,
                                it

                            )
                        }
                        theMovieHasBeenLove = true
                    }
                }
            }
        }
        lifecycleScope.launch {
            val getListActor =
                filmId?.let { viewModel.getListActor(it) }
            val ActorAdapter = getListActor?.let { ActorDirectorAdapter(it) { onItemClick(it) } }
            binding.recyclerViewActor.adapter = ActorAdapter

            val getListDirector =
                filmId?.let { viewModel.getListDirector(it)}
            val DirectorAdapter =
                getListDirector?.let { ActorDirectorAdapter(it) { onItemClick(it) } }
            binding.recyclerViewAllSerialDirector.adapter = DirectorAdapter

            val getListGallery =
                filmId?.let { viewModel.getListGallery(it, "STILL")}
            val GalleryAdapter = getListGallery?.let { GalleryAdapter(it) }
            binding.recyclerViewAllSerialGallery.adapter = GalleryAdapter

            val getListRelatedMovies =
                filmId?.let { viewModel.getListRelatedMovies(it) }
            if (getListRelatedMovies != null) {
                binding.buttonAllSimilar.text = getListRelatedMovies.size.toString()
            }
            val RelatedMoviesAdapter =
                getListRelatedMovies?.let { SimilarMovieAdapter(it) { onItemClickSimilar(it) } }
            binding.recyclerViewAllSerialSimilar.adapter = RelatedMoviesAdapter

        }
        return binding.root
    }

    private fun onItemClick(item: ActorDirector) {
        val bundle = Bundle().apply {
            filmId?.let { putInt("filmId", it) }
            item.staffId?.let { putInt("staffId", it) }
        }
        findNavController().navigate(
            R.id.action_serialPageFragment_to_actorInformationFragment,
            bundle
        )
    }

    private fun onItemClickSimilar(item: SimilarMovie) {
        val bundle = Bundle().apply {
            putInt("filmId", item.filmId)
        }
        findNavController().navigate(R.id.action_serialPageFragment_self, bundle)
    }
}