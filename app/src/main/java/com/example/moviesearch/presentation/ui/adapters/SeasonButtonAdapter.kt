package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ButtonForSeasonIthemBinding

class SeasonButtonAdapter (private val data: List<String>,
                            private val onClick: (String) -> Unit
) :
    RecyclerView.Adapter<SeasonButtonViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeasonButtonViewHolder {
        val binding = ButtonForSeasonIthemBinding.inflate(LayoutInflater.from(parent.context))
        return SeasonButtonViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SeasonButtonViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            item?.let {
                if (item != null) {
                    buttonTypePhoto.text = item
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

class SeasonButtonViewHolder(val binding: ButtonForSeasonIthemBinding) :
    RecyclerView.ViewHolder(binding.root)