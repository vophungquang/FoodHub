package com.example.team10.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team10.R
import com.example.team10.databinding.ActivityFragmentTopBinding
import kotlinx.android.synthetic.main.activity_fragment_top.*

class FragmentTop : Fragment() {
    private lateinit var binding: ActivityFragmentTopBinding
    private lateinit var adapter: RestaurantAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_top, container, false)
        adapter = RestaurantAdapter()
        binding.RestaurantList.adapter = adapter
        adapter.data = getdataSet()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RestaurantList.layoutManager = LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()
    }


}
