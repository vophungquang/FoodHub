package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvUsername : TextView
    private lateinit var tvNameUser : TextView
    private lateinit var btnBack : ImageView
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtNewPassword : EditText
    private lateinit var btnUpdate : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)
        findViewById()
        val username = intent.getStringExtra("user_name")
        val id = intent.getStringExtra("_id")
        val password = intent.getStringExtra("password")
        val email = intent.getStringExtra("email")
        tvUsername.text = username
        tvNameUser.text = username
        edtEmail.setText(email)

        btnBack.setOnClickListener{
            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            intent.putExtra("_id", id)
            intent.putExtra("user_name", username)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            startActivity(intent)
        }
        btnUpdate.setOnClickListener{
            if(edtPassword.text.toString() == password) {
                var newEmail = edtEmail.text.toString()
                var newPassword = edtNewPassword.text.toString()
                if (username != null) {
                    updateAccount(username, newEmail, newPassword)
                }
            } else{
                Toast.makeText(this, "Password incorrect, please check again!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun findViewById(){
        tvUsername = findViewById(R.id.tv_username)
        edtEmail = findViewById(R.id.edt_email)
        tvNameUser = findViewById(R.id.tv_user_name)
        btnBack = findViewById(R.id.img_back)
        edtPassword = findViewById(R.id.edt_password)
        edtNewPassword = findViewById(R.id.edt_new_password)
        btnUpdate = findViewById(R.id.btn_update)
    }

    private fun updateAccount(username: String, email: String, password: String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://genxshopping.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(APIService::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.updateAccount(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val item = response.body()
                    val status = item?.status ?: "N/A"
                    Log.d("-----STATUS------ :", status)
                    if(status == "Account has been updated") {
                        Toast.makeText(this@ProfileActivity, status, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ProfileActivity, SignInActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this@ProfileActivity, status, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }
}