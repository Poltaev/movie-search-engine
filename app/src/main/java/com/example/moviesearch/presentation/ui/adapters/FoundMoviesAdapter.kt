package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.databinding.FoundMoviesItemBinding
import com.example.moviesearch.entity.foundMovies.FoundMovie

class FoundMoviesAdapter (
    private val data: List<FoundMovie>,
//    private val onClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<FoundMovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoundMovieViewHolder {
        val binding = FoundMoviesItemBinding.inflate(LayoutInflater.from(parent.context))
        return FoundMovieViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FoundMovieViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textNameMovie.text = item.nameRu
                textGanreMovie.text = "${item.year}, ${item.genres[0].genre}"
                topKinopoisk.text = item.ratingKinopoisk.toString()
            }


            item?.let {
                Glide
                    .with(movieFilmographyButton.context)
                    .load(it.posterUrl)
                    .into(movieFilmographyButton)
            }

        }
//        holder.binding.root.setOnClickListener {
//            item?.let {
//                onClick(item)
//            }
//        }
    }

}

class FoundMovieViewHolder(val binding: FoundMoviesItemBinding) :
    RecyclerView.ViewHolder(binding.root)