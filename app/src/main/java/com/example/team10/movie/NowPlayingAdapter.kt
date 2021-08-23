package com.example.team10.movie

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.team10.R
import com.squareup.picasso.Picasso

class NowPlayingAdapter(var vpq: Context?) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>(){

    private val LIST_ITEM : Int = 0
    private val GRID_ITEM : Int = 1
    private var isSwitch : Boolean = false

    val data = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view : View = if(viewType == GRID_ITEM)
            layoutInflater.inflate(R.layout.linear_movie, parent, false)
        else
            layoutInflater.inflate(R.layout.grid_movie, parent, false)
        return ViewHolder(view)
    }

    fun switchItemView() : Boolean
    {
        isSwitch = !isSwitch
        return isSwitch
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSwitch){
            LIST_ITEM
        }else{
            GRID_ITEM
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = item.title
        holder.detail.text = item.overview
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + item.posterPath).into(holder.ava)
        holder.ava.setOnClickListener{
            val intent = Intent(vpq, DetailMovie::class.java)
            intent.putExtra("vpq",item)
            vpq?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setDataList(listData: List<Movie>){
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ava: ImageView = itemView.findViewById(R.id.restaurant_image)
        val name: TextView = itemView.findViewById(R.id.tvres_name)
        val detail: TextView = itemView.findViewById(R.id.tvaddress)
    }
}