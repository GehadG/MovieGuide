package com.neugelb.moviedirectory.ui.discover

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.neugelb.moviedirectory.R
import com.neugelb.moviedirectory.databinding.FragmentDiscoverBinding
import com.neugelb.moviedirectory.model.MovieResult
import com.neugelb.moviedirectory.util.Resource
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {
    private val binding by viewBinding(FragmentDiscoverBinding::bind)
    private val viewModel by viewModels<DiscoverViewModel>()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieListAdapter = MovieListAdapter(onItemClicked = {
            onItemClick(it)
        })
        val searchListAdapter = SearchListAdapter(onItemClicked = {
            onItemClick(it)
        })
        binding.fragmentToolbar.inflateMenu(R.menu.menu)
        setupSearchBar(searchListAdapter, movieListAdapter)
        movieListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                textTop.isVisible = movieListAdapter.itemCount != 0
                progressLogo.isVisible = loadState.mediator?.refresh is LoadState.Loading
            }
        }
        binding.apply {
            recyclerView.apply {
                adapter = movieListAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getMovies().collectLatest {
                movieListAdapter.submitData(lifecycle, it)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun setupSearchBar(
        searchListAdapter: SearchListAdapter,
        movieListAdapter: MovieListAdapter
    ) {
        val search = binding.fragmentToolbar.menu.findItem(R.id.app_bar_search)
        val searchView = search.actionView as SearchView
        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                binding.apply {
                    recyclerView.apply {
                        adapter = searchListAdapter
                        layoutManager = LinearLayoutManager(context)
                        textTop.text = context.getString(R.string.search_results)
                    }
                }
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                binding.apply {
                    recyclerView.apply {
                        adapter = movieListAdapter
                        layoutManager = LinearLayoutManager(context)
                        textTop.text = context.getString(R.string.top_movies)
                    }
                }
                return true
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    viewModel.searchMovies(query).observe(viewLifecycleOwner) {
                        if (it is Resource.Success) {
                            searchListAdapter.submitList(it.data)
                        }
                    }
                }
                return false
            }
        })
    }

    private fun onItemClick(movieResult: MovieResult) {
        val action =
            DiscoverFragmentDirections.actionDiscoverToDetail(movieResult.id.toString())
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.cancel()
    }


}