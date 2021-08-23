package com.example.team10.rest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {
    private var api : MovieDBService

    val API : MovieDBService
        get() = api

    init {
        api = createMovieDBService()
    }

    companion object {
        private var mInstance: RestClient? = null

        fun getInstance() = mInstance ?: synchronized(this){
            mInstance ?: RestClient().also { mInstance = it }
        }
    }

    private fun createMovieDBService() : MovieDBService{
        //create okhttp
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .build()

        //create retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(MovieDBService::class.java)
    }
}