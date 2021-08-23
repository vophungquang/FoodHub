package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var tvType: TextView
    private lateinit var imgBack: ImageView
    private lateinit var btnCart: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_product)
        findViewById()
        manager = GridLayoutManager(this,2)
        val typeProduct = intent.getStringExtra("type")
        val username = intent.getStringExtra("user_name")
        val id = intent.getStringExtra("_id")
        val password = intent.getStringExtra("password")
        val email = intent.getStringExtra("email")
        if (typeProduct != null) {
            getAllProduct(typeProduct)
            tvType.text = typeProduct.toUpperCase()
        }
        imgBack.setOnClickListener{
            val intent = Intent(this@ProductActivity, HomeActivity::class.java)
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            intent.putExtra("password", password)
            intent.putExtra("email", email)
            startActivity(intent)
        }
        btnCart.setOnClickListener{
            val intent = Intent(this@ProductActivity, CartActivity::class.java)
            intent.putExtra("user_name", username)
            startActivity(intent)
        }
    }

    private fun findViewById(){
        tvType = findViewById(R.id.tv_type)
        btnCart = findViewById(R.id.img_cart)
        imgBack = findViewById(R.id.img_back)
    }

    private fun getAllProduct(type: String){
        Api.retrofitService.getProduct(type).enqueue(object: Callback<List<Product>> {
            override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
            ) {
                if(response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.recycle_view).apply{
                        myAdapter = ProductAdapter(response.body()!!, this@ProductActivity)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                    Log.d("-----Pretty Printed JSON------ :", response.toString())
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}