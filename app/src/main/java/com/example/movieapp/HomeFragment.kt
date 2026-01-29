package com.example.movieapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        setupSearch()
        fetchMovies() // Initial load
    }

    private fun setupSearch() {
        val searchView = binding.searchView

        try {
            val searchText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
            val closeBtn = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

            searchText.setTextColor(Color.WHITE)
            searchText.setHintTextColor(Color.LTGRAY)
            searchIcon.setColorFilter(Color.WHITE)
            closeBtn.setColorFilter(Color.WHITE)
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error styling search view", e)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchMovies(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    fetchMovies()
                } else {
                    fetchMovies(newText)
                }
                return true
            }
        })
    }

    private fun fetchMovies(query: String? = null) {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = if (query == null) {
                    RetrofitInstance.api.getPopularMovies()
                } else {
                    RetrofitInstance.api.searchMovies(query = query)
                }

                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    movieAdapter.setMovies(movies)

                    binding.rvMovies.visibility = if (movies.isNotEmpty()) View.VISIBLE else View.GONE
                    binding.layoutEmptyState.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching movies: ${e.message}")
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(emptyList())
        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}