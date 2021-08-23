package com.example.team10.activity.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.team10.DataStore
import com.example.team10.User


class ProfileViewModel : ViewModel() {
    var user = MutableLiveData<User>()
    private val dataStore = DataStore.instance

    init {
        user.value = User()
    }

    fun setupUserProfile(email: String){
        val user =  dataStore.getUserByEmail(email)
        user?.let {
            this.user.value = user
            this.user.postValue(user)
        }

    }

    fun editFullNameUser(email: String, fullName: String) {
        dataStore.editUser(email,DataStore.FULL_NAME_FIELD,fullName)
        user.value?.fullName = fullName
        user.postValue(user.value)
    }

    fun editEmailUser(oldEmail:String, email: String) {
        dataStore.editUser(oldEmail,DataStore.EMAIL_FIELD,email)
        user.value?.email = email
        user.postValue(user.value)
    }

    fun editPhoneNumberUser(email: String, phoneNumber: String) {
        dataStore.editUser(email,DataStore.PHONE_NUMBER_FIELD,phoneNumber)
        user.value?.phoneNumber = phoneNumber
        user.postValue(user.value)
    }
}