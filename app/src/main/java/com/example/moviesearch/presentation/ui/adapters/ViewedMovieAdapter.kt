package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.ViewedMovie
import com.example.moviesearch.databinding.MovieSimilarItemBinding
import com.example.moviesearch.databinding.MovieViewedItemBinding
import com.example.moviesearch.entity.similars.SimilarMovie

class ViewedMovieAdapter (
    private val data: List<ViewedMovie>,
    private val onClick: (ViewedMovie) -> Unit
) :
    RecyclerView.Adapter<MovieViewedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewedViewHolder {
        val binding = MovieViewedItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieViewedViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieViewedViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                if (item.nameMovie == "11111" ){
                    textNameMovie.isVisible = false
                    textGenre.isVisible = false
                    textKinopoiskId.isVisible = false
                    backgroundView.isVisible = false
                }else {
                    textNameMovie.text = item.nameMovie
                    textGenre.text = item.genre
                    textKinopoiskId.text = item.ratingKinopoisk.toString()
                }
            }

            item?.let {
                if (item.nameMovie == "11111" ){
                    Glide
                        .with(moviefromKinopoiskButton.context)
                        .load(R.drawable.delete_all)
                        .into(moviefromKinopoiskButton)
                }else {
                    Glide
                        .with(moviefromKinopoiskButton.context)
                        .load(it.uriStringPhoto)
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

class MovieViewedViewHolder(val binding: MovieViewedItemBinding) :
    RecyclerView.ViewHolder(binding.root)