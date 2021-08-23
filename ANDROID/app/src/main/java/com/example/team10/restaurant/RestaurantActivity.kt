package com.example.team10.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team10.R
import com.example.team10.databinding.ActivityRestaurantBinding
import kotlinx.android.synthetic.main.activity_restaurant.*


class RestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var viewModel: RestaurantViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant)

        val adapter = RestaurantAdapter()
        binding.RestaurantList.adapter = adapter
        adapter.data = getdataSet()

        //stackoverflow
        btn_switch.setOnClickListener {
            adapter.switchView()
            if (adapter.switchView()) {
                RestaurantList.layoutManager = GridLayoutManager(this, 2)
            } else {
                RestaurantList.layoutManager = LinearLayoutManager(this)
            }
            adapter.switchView()
            adapter.notifyDataSetChanged()

        }
    }
}
