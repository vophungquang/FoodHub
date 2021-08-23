package com.example.team10

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var fullName: String = "", var email: String = "", var password: String= "", var phoneNumber: String = "") : Parcelable