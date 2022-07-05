package com.neugelb.moviedirectory.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neugelb.moviedirectory.databinding.GenreItemBinding
import com.neugelb.moviedirectory.model.Genre


class GenreAdapter :
    ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenericComparitor<Genre>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class GenreViewHolder(private val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: Genre) {
            binding.apply {
                binding.text.text = genre.name
            }
        }
    }

}

class GenericComparitor<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem
}