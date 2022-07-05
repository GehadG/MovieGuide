package com.neugelb.moviedirectory.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neugelb.moviedirectory.R
import com.neugelb.moviedirectory.databinding.MovieItemBinding
import com.neugelb.moviedirectory.model.MovieResult


class MovieListAdapter(val onItemClicked: (MovieResult) -> Unit) :
    PagingDataAdapter<MovieResult, MovieListAdapter.MovieItemViewHolder>(MovieComparitor()) {

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

    class MovieItemViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieResult) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/original${movie.posterPath}")
                    .error(R.drawable.unavailable)
                    .into(movieImage)
                title.text = movie.title
                releaseDate.text = movie.releaseDate
                upvotes.text = movie.voteCount.toString()
            }
        }
    }

    class MovieComparitor : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult) =
            oldItem == newItem
    }
}
