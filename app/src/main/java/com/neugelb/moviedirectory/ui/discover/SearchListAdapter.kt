package com.neugelb.moviedirectory.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.neugelb.moviedirectory.databinding.MovieItemBinding
import com.neugelb.moviedirectory.model.MovieResult
import com.neugelb.moviedirectory.ui.discover.MovieListAdapter.MovieComparitor
import com.neugelb.moviedirectory.ui.discover.MovieListAdapter.MovieItemViewHolder


class SearchListAdapter(val onItemClicked: (MovieResult) -> Unit) :
    ListAdapter<MovieResult, MovieItemViewHolder>(MovieComparitor()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            holder.itemView.setOnClickListener { onItemClicked(currentItem) }
        }
    }
}
