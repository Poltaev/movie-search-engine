package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.HistoryMovieActor
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.databinding.MovieActorHistoryItemBinding
import com.example.moviesearch.databinding.MovieSimilarItemBinding
import com.example.moviesearch.databinding.MovieViewedItemBinding
import com.example.moviesearch.entity.similars.SimilarMovie

class HistoryActorMovieAdapter(
    private val data: List<HistoryMovieActor>,
    private val onClick: (HistoryMovieActor) -> Unit
) :
    RecyclerView.Adapter<MovieHistoryActorViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHistoryActorViewHolder {
        val binding = MovieActorHistoryItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieHistoryActorViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieHistoryActorViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                if (item.nameMovieActor == "11111") {
                    textNameMovieActor.isVisible = false
                    textGenreProfession.isVisible = false
                    backgroundView.isVisible = false
                } else {
                    textNameMovieActor.text = item.nameMovieActor
                    textGenreProfession.text = item.genreActor

                }
            }

            item?.let {
                if (item.nameMovieActor == "11111") {
                    Glide
                        .with(moviefromKinopoiskButton.context)
                        .load(R.drawable.delete_all)
                        .into(moviefromKinopoiskButton)
                } else {
                    Glide
                        .with(moviefromKinopoiskButton.context)
                        .load(it.uriStringPhotoActor)
                        .into(moviefromKinopoiskButton)
                }
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

}

class MovieHistoryActorViewHolder(val binding: MovieActorHistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root)