package com.example.movieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemMovieBinding

class MovieAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.binding.apply {
            tvMovieTitle.text = movie.title
            tvMovieRating.text = String.format("%.1f", movie.vote_average)
            val posterUrl = "${Constants.IMAGE_BASE_URL}${movie.poster_path}"

            Glide.with(holder.itemView.context)
                .load(posterUrl)
                .placeholder(R.drawable.ic_movie_placeholder)
                .error(R.drawable.ic_error)
                .error(android.R.drawable.stat_notify_error)
                .centerCrop()
                .into(ivMoviePoster)
        }

        holder.itemView.setOnClickListener { view ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(movie)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun setMovies(newList: List<Movie>) {
        this.movies = newList
        notifyDataSetChanged()
    }
}