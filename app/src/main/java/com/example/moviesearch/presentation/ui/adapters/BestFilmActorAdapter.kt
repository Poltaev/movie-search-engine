package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.databinding.MovieBestFilmActorItemBinding
import com.example.moviesearch.entity.informationPerson.Movie

class BestFilmActorAdapter (
    private val data: List<Movie>,
    private val dataUrlPhoto : List<String>,
    private val onClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<BestFilmActorViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestFilmActorViewHolder {
        val binding = MovieBestFilmActorItemBinding.inflate(LayoutInflater.from(parent.context))
        return BestFilmActorViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BestFilmActorViewHolder, position: Int) {
        val item = data.getOrNull(position)
        val item1 = dataUrlPhoto.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textNameMovie.text = item.nameRu
                textGanreMovie.text = item.professionKey
                topKinopoisk.text = item.rating
            }


            item1?.let {
                Glide
                    .with(movieFilmographyButton.context)
                    .load(it)
                    .into(movieFilmographyButton)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

}

class BestFilmActorViewHolder(val binding: MovieBestFilmActorItemBinding) :
    RecyclerView.ViewHolder(binding.root)