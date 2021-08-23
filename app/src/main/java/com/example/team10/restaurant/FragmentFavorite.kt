package com.example.team10.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team10.R
import com.example.team10.databinding.ActivityFragmentFavoriteBinding
import kotlinx.android.synthetic.main.activity_fragment_favorite.*

class FragmentFavorite(val data : List<Restaurant>)  : Fragment(){
    private lateinit var binding : ActivityFragmentFavoriteBinding
    private lateinit var adapter : FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_favorite,container,false)
        adapter = FavoriteAdapter(data)
        binding.RestaurantList1.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RestaurantList1.layoutManager = LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()
    }

}