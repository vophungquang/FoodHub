package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignIn : Button
    private lateinit var btnSignUp : TextView
    private lateinit var edtUsername : EditText
    private lateinit var edtPassword : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        findViewById()
        val username = intent.getStringExtra("user_name")
        val password = intent.getStringExtra("password")
        if(username != null && password != null){
            edtUsername.setText(username)
            edtPassword.setText(password)
        }
        btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        btnSignIn.setOnClickListener{
            signIn()
        }
    }

    private fun findViewById(){
        btnSignUp = findViewById(R.id.tv_sign_up_here)
        btnSignIn = findViewById(R.id.btn_sign_in)
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
    }

    private fun signIn() {
        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://genxshopping.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        val username = edtUsername?.text.toString().trim()
        val password = edtPassword?.text.toString().trim()

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.signInUser(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val item = response.body()
                    val status = item?.status ?: "N/A"
                    val id = item?.id ?: "N/A"
                    val email = item?.email ?: "N/A"
                    val user = item?.username ?: "N/A"
                    val error = item?.error ?: "N/A"
                    Log.d("-----STATUS------ :", status)
                    Log.d("-----ID------ :", id)
                    Log.d("-----USERNAME------ :", user)
                    Log.d("-----EMAIL------ :", email)
                    if(status == "success"){
                        Toast.makeText(this@SignInActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        intent.putExtra("user_name", user)
                        intent.putExtra("_id", id)
                        intent.putExtra("password", password)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this@SignInActivity,
                            "$status. Please check your email and password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

}