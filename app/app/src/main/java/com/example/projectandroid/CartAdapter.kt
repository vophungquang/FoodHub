package com.example.projectandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import java.lang.Integer.parseInt

class CartAdapter(private val data: List<Cart>, var context: Context) : RecyclerView.Adapter<CartAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        private var itemClickListener: ItemClickListener? = null
        lateinit var btnPlus : Button
        lateinit var btnMinus : Button
        lateinit var tvCount : TextView
        lateinit var tvTotal : TextView
        lateinit var tvPrice : TextView
        fun setItemClickListener(itemClickListener: ItemClickListener?) {
            this.itemClickListener = itemClickListener
        }
        fun bind(cart: Cart){
            val tvType = view.findViewById<TextView>(R.id.tv_type)
            val imgAvatar = view.findViewById<ImageView>(R.id.img_avatar)
            val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
            tvTotal = view.findViewById(R.id.tv_total)
            tvCount = view.findViewById(R.id.tv_count)
            tvPrice = view.findViewById(R.id.tv_price)

            btnPlus = view.findViewById(R.id.btn_plus)
            btnMinus = view.findViewById(R.id.btn_minus)

            tvType.text = cart.name
            tvTotal.text = cart.total
            tvCount.text = cart.count
            tvPrice.text = cart.price

            Glide.with(view.context).load(cart.link).centerCrop().into(imgAvatar)
            btnConfirm.setOnLongClickListener(this)
            btnConfirm.setOnClickListener(this)
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
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(data[position])
        val cart : Cart = item
        holder.btnPlus.setOnClickListener{
            holder.tvCount.text = (parseInt(holder.tvCount.text.toString()) + 1).toString()
            var price = holder.tvPrice.text.toString()
            var newPrice = parseInt(price.replace("$", ""))
            var total = holder.tvTotal.text.toString()
            var newTotal = parseInt(total.replace("$", ""))
            holder.tvTotal.text = "$" + (newTotal + newPrice).toString()
        }
        holder.btnMinus.setOnClickListener{
            if(parseInt(holder.tvCount.text.toString()) > 0){
                holder.tvCount.text = (parseInt(holder.tvCount.text.toString()) - 1).toString()
                var price = holder.tvPrice.text.toString()
                var newPrice = parseInt(price.replace("$", ""))
                var total = holder.tvTotal.text.toString()
                var newTotal = parseInt(total.replace("$", ""))
                holder.tvTotal.text = "$" + (newTotal - newPrice).toString()
            }
        }

        holder.setItemClickListener(object : ItemClickListener {

            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                if (isLongClick) {
                    Log.e("-------------------", "longClick")
                } else {
                    val count = parseInt(holder.tvCount.text.toString())
                    val userIntent = (view?.getContext() as Activity).intent
                    val username = userIntent.getStringExtra("user_name")
                    val name = cart.name
                    if (username != null) {
                        updateCount(count, username, name)
                    }
                    else{
                        Log.e("-------117CartAdapter---------", "FAILED")
                    }
                }
            }
        })
    }

    fun updateCount(count: Int, username: String, name: String){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://genxshopping.herokuapp.com")
                .build()

        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("name", name)
        jsonObject.put("count", count)

        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.updateCount(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Update Product Successful", Toast.LENGTH_SHORT).show()
                    Log.e("Status", "Update Successful")
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }
}