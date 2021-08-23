package com.example.team10.restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.team10.R
import com.squareup.picasso.Picasso

class FavoriteAdapter(val data : List<Restaurant>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.restaurant_item, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tvResname.text=item.res_name
        holder.tvResAddr.text=item.address
        Picasso.get().load(item.avatar).into(holder.imgRes)

    }

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvResname=itemView.findViewById<TextView>(R.id.tvres_name)
        //var tvResname: TextView = itemView.findViewById(R.id.tvres_name)
        val tvResAddr=itemView.findViewById<TextView>(R.id.tvaddress)
        //val imgRes=itemView.findViewById<TextView>(R.id.restaurant_image)
        //var tvResAddr: TextView = itemView.findViewById(R.id.tvaddress)
        val imgRes = itemView.findViewById<ImageView>(R.id.restaurant_image)!!
    }
}