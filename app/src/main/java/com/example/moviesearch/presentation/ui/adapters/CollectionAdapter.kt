package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.databinding.ActorInformationItemBinding
import com.example.moviesearch.databinding.CollectionItemBinding
import com.example.moviesearch.domain.DataBaseUseCase

import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import kotlinx.coroutines.CoroutineScope

class CollectionAdapter(
    private val data: List<Collection>,
    private val onClick: (Collection) -> Unit
) :
    RecyclerView.Adapter<CollectionViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionViewHolder {

        val binding = CollectionItemBinding.inflate(LayoutInflater.from(parent.context))
        return CollectionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val response = data
        val item = response?.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textNameCollection.text = item?.nameCollection

                when (item.nameCollection) {
                    "Любимые" -> {
                        buttonDeleteCollection.isVisible = false
                        item?.let {
                            Glide
                                .with(imageCollection.context)
                                .load(R.drawable.baseline_favorite_collection)
                                .into(imageCollection)
                        }
                    }

                    "Хочу посмотреть" -> {
                        buttonDeleteCollection.isVisible = false
                        item?.let {
                            Glide
                                .with(imageCollection.context)
                                .load(R.drawable.baseline_bookmark_collection)
                                .into(imageCollection)
                        }
                    }

                    else -> {
                        item?.let {
                            Glide
                                .with(imageCollection.context)
                                .load(R.drawable.baseline_person_collection)
                                .into(imageCollection)
                        }
                    }
                }
            }

        }
holder.binding.root.isContextClickable
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}

class CollectionViewHolder(val binding: CollectionItemBinding) :
    RecyclerView.ViewHolder(binding.root)