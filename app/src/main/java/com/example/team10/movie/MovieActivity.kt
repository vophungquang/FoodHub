package com.example.team10.movie

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team10.R
import kotlinx.android.synthetic.main.activity_restaurant.*
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        loadFragment(NowPlaying())

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nowplaying->{
                    loadFragment(NowPlaying())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.toprate->{
                    loadFragment(TopRated())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.change,menu)
        return true
    }

    private fun loadFragment(fragment : Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frag_movie,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}