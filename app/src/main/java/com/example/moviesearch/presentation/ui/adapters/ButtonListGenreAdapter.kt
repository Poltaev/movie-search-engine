package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ButtonListGenreIthemBinding
import com.example.moviesearch.entity.listContryGenre.IdGenre

class ButtonListGenreAdapter (
    private val data: List<IdGenre>,
    private val onClick: (IdGenre) -> Unit
) :
    RecyclerView.Adapter<ButtonListGenreViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonListGenreViewHolder {
        val binding = ButtonListGenreIthemBinding.inflate(LayoutInflater.from(parent.context))
        return ButtonListGenreViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ButtonListGenreViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                buttonListGenre.text = item.genre
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

}

class ButtonListGenreViewHolder(val binding: ButtonListGenreIthemBinding) :
    RecyclerView.ViewHolder(binding.root)