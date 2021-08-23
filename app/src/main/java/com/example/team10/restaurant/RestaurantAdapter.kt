package com.example.team10.restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.team10.R
import com.squareup.picasso.Picasso

class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    var data: List<Restaurant> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.restaurant_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tvResname.text = item.res_name
        holder.tvResAddr.text = item.address
        Picasso.get().load(item.avatar).into(holder.imgRes)

        if (dsLike.contains(data[position])) holder.Like.isChecked = true
        holder.Like.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!dsLike.contains(data[position]))
                    dsLike += data[position]
            } else {
                dsLike -= data[position]
            }
        }
    }


    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvResname = itemView.findViewById<TextView>(R.id.tvres_name)
        //var tvResname: TextView = itemView.findViewById(R.id.tvres_name)
        val tvResAddr = itemView.findViewById<TextView>(R.id.tvaddress)
        //val imgRes=itemView.findViewById<TextView>(R.id.restaurant_image)
        //var tvResAddr: TextView = itemView.findViewById(R.id.tvaddress)
        val imgRes = itemView.findViewById<ImageView>(R.id.restaurant_image)
        val Like = itemView.findViewById<CheckBox>(R.id.favorite_btn)
    }
}


