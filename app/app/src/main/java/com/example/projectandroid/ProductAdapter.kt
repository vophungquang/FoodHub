package com.example.projectandroid

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductAdapter(private val data: List<Product>, var context: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        private var itemClickListener: ItemClickListener? = null
        fun setItemClickListener(itemClickListener: ItemClickListener?) {
            this.itemClickListener = itemClickListener
        }
        fun bind(product: Product){
            val tvType = view.findViewById<TextView>(R.id.tv_type)
            val imgAvatar = view.findViewById<ImageView>(R.id.img_avatar)
            val tvPrice = view.findViewById<TextView>(R.id.tv_price)
            val imgCart = view.findViewById<ImageView>(R.id.icon_cart)

            tvType.text = product.name
            tvPrice.text = product.price

            Glide.with(view.context).load(product.link).centerCrop().into(imgAvatar)
            imgCart.setOnClickListener(this)
            imgCart.setOnLongClickListener(this)
        }
        override fun onClick(v: View?) {
            itemClickListener?.onClick(v, adapterPosition, false)
        }

        override fun onLongClick(v: View?): Boolean {
            itemClickListener?.onClick(v, adapterPosition, true)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(data[position])
        val product : Product = item

        holder.setItemClickListener(object : ItemClickListener {

            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                if (isLongClick) {
                    val retrofit = Retrofit.Builder()
                            .baseUrl("http://genxshopping.herokuapp.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                    val service = retrofit.create(APIService::class.java)

                    val userIdIntent = (view?.getContext() as Activity).intent
                    val username = userIdIntent.getStringExtra("user_name")
                    val id = userIdIntent.getStringExtra("_id")
                    val price = product.price
                    val name = product.name
                    val link = product.link

                    val jsonObject = JSONObject()
                    jsonObject.put("_id", id)
                    jsonObject.put("username", username)
                    jsonObject.put("name", name)
                    jsonObject.put("price", price)
                    jsonObject.put("link", link)
                    val jsonObjectString = jsonObject.toString()
                    val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                    CoroutineScope(Dispatchers.IO).launch {
                        val response = service.postCart(requestBody)
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                val item = response.body()
                                val status = item?.status ?: "N/A"
                                Log.e("-------STATUS---------", status)
                                if (status == "success") {
                                    Toast.makeText(context, "Add to cart successfully", Toast.LENGTH_SHORT).show()
                                } else {

                                }

                            } else {
                                Log.e("RETROFIT_ERROR", response.code().toString())
                            }
                        }
                    }
                    if (username != null && id != null) {
                        Log.e("-------USERNAME---------", username)
                        Log.e("-------ID---------", id)
                        Log.e("-------NAME---------", product.name)
                        Log.e("-------PRICE---------", product.price)
                        Log.e("-------LINK---------", product.link)
                    }
                } else {
                    Log.e("-------NGUYENVANSON---------", "OnClick")
                }
            }
        })
    }
}


