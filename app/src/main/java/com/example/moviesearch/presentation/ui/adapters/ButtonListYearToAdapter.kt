package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ButtonListYearToIthemBinding

class ButtonListYearToAdapter (
    private val data: List<Int>,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<ButtonListYearToViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonListYearToViewHolder {
        val binding = ButtonListYearToIthemBinding.inflate(LayoutInflater.from(parent.context))
        return ButtonListYearToViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ButtonListYearToViewHolder, position: Int) {
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

class ButtonListYearToViewHolder(val binding: ButtonListYearToIthemBinding) :
    RecyclerView.ViewHolder(binding.root)