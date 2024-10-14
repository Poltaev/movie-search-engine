package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.Collection
import com.example.moviesearch.dataBase.IdCollectionAndMovie
import com.example.moviesearch.databinding.ActorInformationItemBinding
import com.example.moviesearch.databinding.AddCollectionItemBinding
import com.example.moviesearch.databinding.CollectionItemBinding
import com.example.moviesearch.domain.DataBaseUseCase

import com.example.moviesearch.entity.actorDirectorList.ActorDirector
import kotlinx.coroutines.CoroutineScope


class AddCollectionAdapter(
    private val data: List<Collection>,
    private val onClick: (Collection) -> Unit
) :
    RecyclerView.Adapter<AddCollectionViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddCollectionViewHolder {

        val binding = AddCollectionItemBinding.inflate(LayoutInflater.from(parent.context))
        return AddCollectionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AddCollectionViewHolder, position: Int) {
        val response = data
        val item = response.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                checkBoxCollection.text = item.nameCollection
            }
        }
        holder.binding.checkBoxCollection.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}

class AddCollectionViewHolder(val binding: AddCollectionItemBinding) :
    RecyclerView.ViewHolder(binding.root)