package com.example.movieapp
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    val results: List<Movie>
) : Parcelable


@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val vote_average: Double,
    val release_date: String
) : Parcelable

