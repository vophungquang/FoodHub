package com.example.team10.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.team10.R
import com.example.team10.databinding.ActivityRestaurantBinding
import com.example.team10.restaurant.RestaurantViewModel



class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var viewModel: RestaurantViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_restaurant)


    }
}