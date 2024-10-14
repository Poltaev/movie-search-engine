package com.example.moviesearch.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.databinding.ActorInformationItemBinding

import com.example.moviesearch.entity.actorDirectorList.ActorDirector

class ActorDirectorAdapter(
    private val data: List<ActorDirector>,
    private val onClick: (ActorDirector) -> Unit
) :
    RecyclerView.Adapter<ActorInformationViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorInformationViewHolder {

        val binding = ActorInformationItemBinding.inflate(LayoutInflater.from(parent.context))
        return ActorInformationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ActorInformationViewHolder, position: Int) {
        val response = data
        val item = response?.getOrNull(position)
        with(holder.binding) {
            if (item != null) {
                textViewNameActor.text = item?.nameRu.toString()
            }

            if (item != null) {
                textViewRoleActor.text = item?.description.toString()
            }
            item?.let {
                Glide
                    .with(imageViewPhotoActor.context)
                    .load(it.posterUrl)
                    .into(imageViewPhotoActor)
            }
        }

        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}

class ActorInformationViewHolder(val binding: ActorInformationItemBinding) :
    RecyclerView.ViewHolder(binding.root)