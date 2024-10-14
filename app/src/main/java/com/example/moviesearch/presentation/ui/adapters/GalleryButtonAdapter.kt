package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ButtonForGalleryIthemBinding

class GalleryButtonAdapter (private val data: List<String>,
                            private val onClick: (String) -> Unit
) :
    RecyclerView.Adapter<GalleryButtonViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryButtonViewHolder {
        val binding = ButtonForGalleryIthemBinding.inflate(LayoutInflater.from(parent.context))
        return GalleryButtonViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: GalleryButtonViewHolder, position: Int) {
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

class GalleryButtonViewHolder(val binding: ButtonForGalleryIthemBinding) :
    RecyclerView.ViewHolder(binding.root)