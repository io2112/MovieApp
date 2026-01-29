package com.example.movieapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)


        val movie = args.movie

        binding.apply {
            tvDetailsTitle.text = movie.title
            tvDetailsOverview.text = movie.overview

            val posterUrl = "${Constants.IMAGE_BASE_URL}${movie.poster_path}"

            Glide.with(this@DetailsFragment)
                .load(posterUrl)
                .into(ivDetailsPoster)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}