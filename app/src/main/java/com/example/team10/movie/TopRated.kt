package com.example.team10.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team10.R
import com.example.team10.databinding.MovieFragmentBinding

class TopRated : Fragment() {
    private lateinit var binding : MovieFragmentBinding
    private lateinit var adapter: TopRatedAdapter
    private lateinit var viewModel : TopRatedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(TopRatedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_fragment, container, false)

        viewModel.getData()
        viewModel.movieList.observe(viewLifecycleOwner, {
            adapter.setDataList(it)
        })

        adapter = TopRatedAdapter(activity)

        binding.apply {
           listMovie.adapter = adapter
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listMovie.layoutManager = LinearLayoutManager(activity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change -> {
                adapter.switchItemView()
                return if (item.title == "List View") {
                    item.title = "Grid View"
                    binding.listMovie.layoutManager = LinearLayoutManager(activity)
                    true
                } else {
                    item.title = "List View"
                    binding.listMovie.layoutManager = GridLayoutManager(activity,2)
                    true
                }
                adapter.notifyDataSetChanged()
            }
            else -> return false
        }
        return super.onOptionsItemSelected(item)

    }
}