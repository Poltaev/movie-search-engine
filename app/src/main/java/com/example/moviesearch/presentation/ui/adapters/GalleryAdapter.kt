package com.example.moviesearch.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.databinding.PhotoForGalleryIthemBinding
import com.example.moviesearch.entity.listPhotoGallery.Items

class GalleryAdapter (private val data: List<Items>
) :
    RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryViewHolder {
        val binding = PhotoForGalleryIthemBinding.inflate(LayoutInflater.from(parent.context))
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(imageViewPhotoGallery.context)
                    .load(it.imageUrl)
                    .into(imageViewPhotoGallery)
            }
        }
    }

}

class GalleryViewHolder(val binding: PhotoForGalleryIthemBinding) :
    RecyclerView.ViewHolder(binding.root)