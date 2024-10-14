package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.FilmForCollection
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.databinding.CollectionFilmViewedItemBinding
import com.example.moviesearch.databinding.MovieActorHistoryItemBinding
import com.example.moviesearch.databinding.MovieSimilarItemBinding
import com.example.moviesearch.databinding.MovieViewedItemBinding
import com.example.moviesearch.entity.similars.SimilarMovie

class FilmFromCollectionMovieAdapter(
    private val data: List<FilmForCollection>,
    private val onClick: (FilmForCollection) -> Unit
) :
    RecyclerView.Adapter<FilmFromCollectionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmFromCollectionViewHolder {
        val binding = CollectionFilmViewedItemBinding.inflate(LayoutInflater.from(parent.context))
        return FilmFromCollectionViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FilmFromCollectionViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textNameMovie.text = item.nameMovie
                textGenre.text = item.genre
                textKinopoiskId.text = item.ratingKinopoisk.toString()

            }

            item?.let {

                    Glide
                        .with(moviefromDataBaseButton.context)
                        .load(it.uriStringPhoto)
                        .into(moviefromDataBaseButton)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

}

class FilmFromCollectionViewHolder(val binding: CollectionFilmViewedItemBinding) :
    RecyclerView.ViewHolder(binding.root)