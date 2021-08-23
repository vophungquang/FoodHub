package com.example.projectandroid

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Cart (
        @SerializedName("name")
        val name: String,
        @SerializedName("count")
        val count: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("total")
        val total: String,
        @SerializedName("link")
        val link: String
): Serializable