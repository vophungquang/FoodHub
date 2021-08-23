package com.example.projectandroid

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Product(
        @SerializedName("name")
        val name: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("link")
        val link: String
        ): Serializable