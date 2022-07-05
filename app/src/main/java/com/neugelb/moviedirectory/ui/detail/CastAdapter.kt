package com.neugelb.moviedirectory.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neugelb.moviedirectory.R
import com.neugelb.moviedirectory.databinding.CastItemBinding
import com.neugelb.moviedirectory.model.Cast


class CastAdapter :
    ListAdapter<Cast, CastAdapter.CastViewHolder>(GenericComparitor<Cast>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding =
            CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CastViewHolder(private val binding: CastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/original${cast.profilePath}")
                    .error(R.drawable.unavailable)
                    .into(img)
                binding.text.text = cast.name
            }
        }
    }

}