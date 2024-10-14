package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.databinding.MovieSimilarItemBinding
import com.example.moviesearch.entity.similars.SimilarMovie

class SimilarMovieAdapter (
    private val data: List<SimilarMovie>,
    private val onClick: (SimilarMovie) -> Unit
) :
    RecyclerView.Adapter<MovieSimilarViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieSimilarViewHolder {
        val binding = MovieSimilarItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieSimilarViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieSimilarViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textNameMovie.text = item.nameRu
            }

            item?.let {
                Glide
                    .with(moviefromKinopoiskButton.context)
                    .load(it.posterUrl)
                    .into(moviefromKinopoiskButton)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

}

class MovieSimilarViewHolder(val binding: MovieSimilarItemBinding) :
    RecyclerView.ViewHolder(binding.root)