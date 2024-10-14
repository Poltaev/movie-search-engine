package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ButtonListCountryIthemBinding
import com.example.moviesearch.entity.listContryGenre.IdCountry

class ButtonListCountryAdapter (
    private val data: List<IdCountry>,
    private val onClick: (IdCountry) -> Unit
) :
    RecyclerView.Adapter<ButtonListCountryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonListCountryViewHolder {
        val binding = ButtonListCountryIthemBinding.inflate(LayoutInflater.from(parent.context))
        return ButtonListCountryViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ButtonListCountryViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                buttonListCountry.text = item.country
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

}

class ButtonListCountryViewHolder(val binding: ButtonListCountryIthemBinding) :
    RecyclerView.ViewHolder(binding.root)