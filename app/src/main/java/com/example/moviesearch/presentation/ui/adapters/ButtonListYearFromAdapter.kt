package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ButtonListYearFromIthemBinding

class ButtonListYearFromAdapter (
    private val data: List<Int>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ButtonListYearFromViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonListYearFromViewHolder {
        val binding = ButtonListYearFromIthemBinding.inflate(LayoutInflater.from(parent.context))
        return ButtonListYearFromViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ButtonListYearFromViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                buttonListYear.text = item.toString()
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }

}

class ButtonListYearFromViewHolder(val binding: ButtonListYearFromIthemBinding) :
    RecyclerView.ViewHolder(binding.root)