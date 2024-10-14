package com.example.moviesearch.presentation.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.databinding.MovieHomeFromKlinopoiskItemBinding
import com.example.moviesearch.entity.listMovieSerial.MovieFromKinopoisk
import java.io.File


//private const val ARG_PARAM_PHOTO = "paramPhoto"

class MovieHomeFromKinopoiskAdapter(
    private val data: List<MovieFromKinopoisk>,
    private val onClick: (MovieFromKinopoisk) -> Unit
) :
    RecyclerView.Adapter<MovieFromKinopoiskViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieFromKinopoiskViewHolder {
        val binding =
            MovieHomeFromKlinopoiskItemBinding.inflate(LayoutInflater.from(parent.context))
        return MovieFromKinopoiskViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieFromKinopoiskViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                if (item?.ratingKinopoisk.toString() == "1.1") {
                    topKinopoisk.isVisible = false
                    topKinopoisk.text = ""
                } else {
                    topKinopoisk.text = item?.ratingKinopoisk.toString()
                }
            }
            if (item != null) {
                if (item.genres[0].genre == "1111") {
                    genreMovie.text = ""
                } else {
                    genreMovie.text = item.genres[0].genre
                }
            }
            if (item != null) {
                if (item.nameRu == "1") {
                    textNameMovie.text = ""
                } else {
                    textNameMovie.text = item.nameRu
                }
            }
            item?.let {
                if (it.posterUrl == "R.drawable.right_arrow") {
                    Glide
                        .with(moviefromKinopoiskButton.context)
                        .load(R.drawable.right_arrow)
                        .into(moviefromKinopoiskButton)
                } else {
                    Glide
                        .with(moviefromKinopoiskButton.context)
                        .load(it.posterUrl)
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

class MovieFromKinopoiskViewHolder(val binding: MovieHomeFromKlinopoiskItemBinding) :
    RecyclerView.ViewHolder(binding.root)