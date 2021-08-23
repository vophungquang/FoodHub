package com.example.team10.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team10.rest.RestClient
import kotlinx.coroutines.launch

class TopRatedViewModel : ViewModel() {

    var movieList = MutableLiveData<List<Movie>>()

    fun getData() {
        viewModelScope.launch {
            val  movieResp = RestClient.getInstance().API.topratedmovie(
                "en-US",
                1,
               )
            Log.e("TAG", movieResp.results.toString())
            movieList.value = movieResp.results!!
        }
    }
}