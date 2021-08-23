package com.example.projectandroid

import com.google.gson.annotations.SerializedName

data class JsonModel(
    @SerializedName("status")
    var status: String?,

    @SerializedName("user_name")
    var username: String?,

    @SerializedName("email")
    var email: String?,

    @SerializedName("id_user")
    var id: String?,

    @SerializedName("link")
    var linkimg: String?,

    @SerializedName("Error")
    var error: String?,

    @SerializedName("total")
    var total: String?,

    @SerializedName("msg")
    var msg: String?

)
