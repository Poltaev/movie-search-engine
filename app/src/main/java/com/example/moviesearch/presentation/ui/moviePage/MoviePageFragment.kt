package com.example.moviesearch.presentation.ui.moviePage

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.databinding.FragmentMoviePageBinding
import com.example.moviesearch.domain.MovieFromKinopoiskUseCase
import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import com.example.moviesearch.entity.filmIdInformation.Countries
import com.example.moviesearch.entity.filmIdInformation.Genres
import com.example.moviesearch.entity.similars.SimilarMovie
import com.example.moviesearch.presentation.GalleryAdapter
import com.example.moviesearch.presentation.ui.adapters.SimilarMovieAdapter
import com.example.moviesearch.presentation.ui.adapters.ActorDirectorAdapter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.math.log

class MoviePageFragment : Fragment() {
    private var _binding: FragmentMoviePageBinding? = null
    private var filmId: Int? = 535341
    private var staffId: Int? = 111
    private var openShortDirection = true
    private var theMovieHasBeenViewed = false
    private var theMovieHasBeenLove = false
    private var theMovieHasBeenWant = false
    private var posterUrl: String? = "poster"
    private var ratingKinopoisk: String? = "rating"
    private var nameRu: String? = "name"
    private var genre: String? = "genre"

    private val viewModel: MoviePageViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return MoviePageViewModel(wordsDao) as T
            }
        }
    }

    private val binding: FragmentMoviePageBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filmId = it.getInt("filmId")
            staffId = it.getInt("staffId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviePageBinding.inflate(inflater)

        lifecycleScope.launch {
            val filmInformation =
                filmId?.let { viewModel.filmInformation(it) }
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

                binding.textViewNameMovie.text = filmInformation.nameRu

                binding.textViewShortInformarionMovie.text =
                    "${filmInformation.ratingKinopoisk}, " +
                            " ${filmInformation.year}, " +
                            "${viewModel.getAllGenre(filmInformation.genres)}" +
                            " ${viewModel.getAllCountry(filmInformation.countries)}" +
                            "${filmInformation.ratingAgeLimits}, " +
                            "${viewModel.translatorOfMinutesIntoHours(filmInformation.filmLength)}"
                binding.textHeadingSerialPage.text = filmInformation.shortDescription
                binding.textInfomationSerialPage.setOnClickListener {
                    if (openShortDirection) {
                        openShortDirection = false
                        binding.textInfomationSerialPage.text =
                            viewModel.getShortDescription(filmInformation.description)
                    } else {
                        openShortDirection = true
                        binding.textInfomationSerialPage.text = filmInformation.description
                    }
                }
                binding.textInfomationSerialPage.text =
                    viewModel.getShortDescription(filmInformation.description)
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
                    R.id.action_moviePageFragment_to_addToCollectionFragment,
                    bundle
                )
            }
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
        binding.buttonWant.setOnClickListener {
            lifecycleScope.launch {
                if (theMovieHasBeenWant) {
                    binding.buttonWant.setImageResource(R.drawable.baseline_bookmark_want_to_see_it)
                    viewModel.getAllCollectionMovie().collect {
                        val filterColection = it.filter { it.collectionId == 2 }
                        filterColection.forEach {
                            if (it.filmId == filmId) {
                                viewModel.deleteCollectionMovie(it)
                            }
                        }
                    }
                    theMovieHasBeenWant = false
                } else {
                    binding.buttonWant.setImageResource(R.drawable.baseline_bookmark_want_to_see_it_already)
                    filmId?.let {
                        viewModel.loadInCollectionMovie(
                            2,
                            it
                        )
                    }
                    theMovieHasBeenWant = true
                }
            }
        }


        binding.buttonBack.setOnClickListener {
            if (staffId == 0) {
                findNavController().navigate(R.id.action_moviePageFragment_to_navigation_home)
            } else {
                val bundle = Bundle().apply {
                    filmId?.let { putInt("filmId", it) }
                    staffId?.let { putInt("staffId", it) }
                }
                findNavController().navigate(
                    R.id.action_moviePageFragment_to_actorInformationFragment,
                    bundle
                )
            }
        }
        lifecycleScope.launch {
            var getListActor =
                filmId?.let { viewModel.getListActor(it) }
            if (getListActor != null) {
                getListActor = getListActor!!.filter { it.professionKey == "ACTOR" }
                binding.buttonAllMovieActor.text = getListActor!!.size.toString()
            }
            var getListDirector =
                filmId?.let { viewModel.getListDirector(it) }
            if (getListActor != null) {
                if (getListDirector != null) {
                    getListDirector = getListDirector!!.filter { it.professionKey != "ACTOR" }
                    binding.buttonAllMovieDirector.text = getListDirector!!.size.toString()
                }
            }
        }

        binding.buttonAllGallery.setOnClickListener {
            val bundle = Bundle()
            if (filmId != null) {
                bundle.putInt("filmId", filmId!!)
            }
            findNavController().navigate(
                R.id.action_moviePageFragment_to_photoGalleryFragment,
                bundle
            )
        }

        lifecycleScope.launch {
            var getListActor =
                filmId?.let { viewModel.getListActor(it) }
            if (getListActor != null) {
                getListActor = getListActor!!.filter { it.professionKey == "ACTOR" }
            }
            val ActorAdapter = getListActor?.let { ActorDirectorAdapter(it) { onItemClick(it) } }
            binding.recyclerViewAllMovieActor.adapter = ActorAdapter
            var getListDirector =
                filmId?.let { viewModel.getListDirector(it) }
            if (getListDirector != null) {
                getListDirector = getListDirector!!.filter { it.professionKey != "ACTOR" }
            }
            val DirectorAdapter =
                getListDirector?.let { ActorDirectorAdapter(it) { onItemClick(it) } }
            binding.recyclerViewAllMovieDirector.adapter = DirectorAdapter

            val getListGallery =
                filmId?.let { viewModel.getListGallery(it, "STILL") }
            val GalleryAdapter = getListGallery?.let { GalleryAdapter(it) }
            binding.recyclerViewAllGallery.adapter = GalleryAdapter

            val getListRelatedMovies = filmId?.let {
                viewModel.getListRelatedMovies(it)
            }
            if (getListRelatedMovies != null) {
                binding.buttonAllSimilar.text = getListRelatedMovies.size.toString()
            }
            val RelatedMoviesAdapter =
                getListRelatedMovies?.let { SimilarMovieAdapter(it) { onItemClickSimilar(it) } }
            binding.recyclerViewAllMovieSimilar.adapter = RelatedMoviesAdapter
        }
        return binding.root
    }

    private fun onItemClick(item: ActorDirector) {
        val bundle = Bundle().apply {
            filmId?.let { putInt("filmId", it) }
            item.staffId?.let { putInt("staffId", it) }
        }
        findNavController().navigate(
            R.id.action_moviePageFragment_to_actorInformationFragment,
            bundle
        )
    }

    private fun onItemClickSimilar(item: SimilarMovie) {
        val bundle = Bundle().apply {
            putInt("filmId", item.filmId)
        }
        findNavController().navigate(R.id.action_moviePageFragment_self, bundle)
    }
}
