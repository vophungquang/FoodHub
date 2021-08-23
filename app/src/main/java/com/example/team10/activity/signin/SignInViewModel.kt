package com.example.team10.activity.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.team10.DataStore
import com.example.team10.User


class SignInViewModel : ViewModel() {
    val user = MutableLiveData<User>()
    val isSignInSucceed = MutableLiveData<User>()
    val errorMessage = MutableLiveData<String>()

    init {
        user.value = User()
    }

    fun login(){
        val dataStore = DataStore.instance
        dataStore.setLoginCallback(loginCallback)
        dataStore.login(user.value!!.email,user.value!!.password)
    }

    fun clear(){
        isSignInSucceed.value = null
        errorMessage.value = null
    }

    private val loginCallback  = object : DataStore.LoginCallback{
        override fun onSucceed(user: User) {
            this@SignInViewModel.isSignInSucceed.value = user
        }

        override fun onFailed(message: String) {
            errorMessage.value = message
        }
    }


}