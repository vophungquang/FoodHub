package com.example.team10.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        loadFragment(FragmentTop())

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_top->{
                    loadFragment(FragmentTop())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ic_favorite->{
                    loadFragment(FragmentFavorite(dsLike))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }

    }


    private fun loadFragment(fragment : Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frag_restaurant,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
