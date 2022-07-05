package com.neugelb.moviedirectory.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neugelb.moviedirectory.R
import com.neugelb.moviedirectory.databinding.FragmentDetailBinding
import com.neugelb.moviedirectory.util.Resource
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genreAdapter = GenreAdapter()
        val castAdapter = CastAdapter()
        binding.apply {
            genresRV.apply {
                adapter = genreAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            castRV.apply {
                adapter = castAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
        viewModel.getMovieDetail(args.movieId).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.apply {
                        progressLogo.isVisible = false
                        detailcontainer.isVisible = true
                        showTitle.text = it.data?.title
                        plotData.text = it.data?.overview
                        try {

                        }catch (exception : Exception){

                        }
                        Glide.with(requireContext())
                            .load("https://image.tmdb.org/t/p/original${it.data?.posterPath}")
                            .error(R.drawable.unavailable)
                            .into(mainImg)
                        genreAdapter.submitList(it.data?.genres)
                        castAdapter.submitList(it.data?.cast)
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        progressLogo.isVisible = false
                        detailcontainer.isVisible = true
                    }
                    Toast.makeText(
                        requireContext(),
                        "An Error Fetching details have occured",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    binding.apply {
                        progressLogo.isVisible = true
                        detailcontainer.isVisible = false
                    }
                }
            }
        }
    }
}