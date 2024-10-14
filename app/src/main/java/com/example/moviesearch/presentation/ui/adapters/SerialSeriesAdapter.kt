package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.SerialSeriesItemBinding
import com.example.moviesearch.entity.SerialSeries.Episodes


class SerialSeriesAdapter(
    private val data: List<Episodes>
) :
    RecyclerView.Adapter<SerialSeriesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SerialSeriesViewHolder {
        val binding = SerialSeriesItemBinding.inflate(LayoutInflater.from(parent.context))
        return SerialSeriesViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SerialSeriesViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textNameSeries.text = "${item.episodeNumber} серия. ${item.nameRu}"
            }
            if (item != null) {
                textViewData.text = item.releaseDate
            }

        }
    }

}

class SerialSeriesViewHolder(val binding: SerialSeriesItemBinding) :
    RecyclerView.ViewHolder(binding.root)