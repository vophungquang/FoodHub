package com.example.projectandroid

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "http://genxshopping.herokuapp.com"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()


interface APIService {

    @GET("/product")
    fun getProduct(@Query("item") item: String?): Call<List<Product>>

    @GET("/cart")
    fun getCart(@Query("username") username: String?): Call<List<Cart>>

    @POST("/signup")
    suspend fun signUpUser(@Body requestBody: RequestBody): Response<JsonModel>

    @POST("/signin")
    suspend fun signInUser(@Body requestBody: RequestBody): Response<JsonModel>

    @POST("/order")
    suspend fun checkOrder(@Body requestBody: RequestBody): Response<JsonModel>

    @POST("/update")
    suspend fun updateAccount(@Body requestBody: RequestBody): Response<JsonModel>

    @POST("/cart")
    suspend fun postCart(@Body requestBody: RequestBody): Response<JsonModel>

    @POST("/count")
    suspend fun updateCount(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/send")
    suspend fun orderProduct(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/showdb")
    suspend fun getUsers(): Response<ResponseBody>

}

object Api {
    val retrofitService: APIService by lazy{retrofit.create(APIService::class.java)}
}