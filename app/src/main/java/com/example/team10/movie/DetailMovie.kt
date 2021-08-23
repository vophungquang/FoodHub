package com.example.team10.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.team10.R
import com.example.team10.databinding.ActivityDetailMovieBinding
import com.squareup.picasso.Picasso

class DetailMovie : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie)
        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)

        val intent = intent
        val movie : Movie = intent.getSerializableExtra("vpq") as Movie
        binding.apply {
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.posterPath).into(imgMovie)
            nameMovie.text = movie.title
            dateCount.text= movie.releaseDate
            rateArrangeCount.text = movie.voteAverage.toString()
            rateCount.text = movie.voteCount.toString()
            popularityCount.text = movie.popularity.toString()
            movieOverview.text = movie.overview

        }
    }

}