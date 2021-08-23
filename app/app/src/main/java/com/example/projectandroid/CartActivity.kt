package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var tvType: TextView
    private lateinit var btnOrder: Button
    private lateinit var btnCheck: Button
    private lateinit var edtCoupon: EditText
    private lateinit var tvTotal: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_cart)
        findViewById()
        tvType.text = "Your Cart"
        manager = LinearLayoutManager(this)
        val username = intent.getStringExtra("user_name")
        val id = intent.getStringExtra("_id")

        if (username != null) {
            getAllCart(username)
        }
        btnOrder.setOnClickListener{
            if (username != null) {
                orderProduct(username)
            }
            else {
                Log.e("-------54CartActivity---------", "FAILED")
            }
        }
        btnCheck.setOnClickListener{
            var couponCode = edtCoupon.text.toString()
            if (username != null) {
                checkProduct(username, couponCode)
            }
            else {
                Log.e("-------62CartActivity---------", "FAILED")
            }
        }
    }

    private fun findViewById(){
        tvType = findViewById(R.id.tv_type)
        btnOrder = findViewById(R.id.btn_order)
        btnCheck = findViewById(R.id.btn_check)
        edtCoupon = findViewById(R.id.edt_coupon)
        tvTotal = findViewById(R.id.tv_total_all)
    }

    private fun getAllCart(username: String){
        Api.retrofitService.getCart(username).enqueue(object: Callback<List<Cart>> {
            override fun onResponse(
                call: Call<List<Cart>>,
                response: Response<List<Cart>>
            ) {
                if(response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.recycle_view).apply{
                        myAdapter = CartAdapter(response.body()!!, this@CartActivity)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun orderProduct(username: String){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://genxshopping.herokuapp.com")
                .build()

        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("username", username)

        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.orderProduct(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CartActivity, "Your order has been successfully, please check your email", Toast.LENGTH_SHORT).show()
                    Log.e("Status", "send email success")
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    private fun checkProduct(username: String, coupon: String){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://genxshopping.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("cupon", coupon)

        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.checkOrder(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val item = response.body()
                    val total = item?.total ?: "N/A"
                    val msg = item?.msg ?: "N/A"
                    tvTotal.text = total
                    Toast.makeText(this@CartActivity, msg, Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

}