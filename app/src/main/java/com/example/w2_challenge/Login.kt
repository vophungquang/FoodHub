package com.example.w2_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email1 = findViewById<EditText>(R.id.edt_email)
        val password1 = findViewById<EditText>(R.id.edt_password)
        val loginbtn = findViewById<Button>(R.id.btn_sign_in)

        loginbtn.setOnClickListener {
            var password = password1.getText().toString()
            var email = email1.getText().toString()
            if (email != "ronaldo@gmail.com" && password != "123456") {
                Toast.makeText(this, "nhap sai ! Vui long nhap lai", Toast.LENGTH_SHORT).show()
                email1.setText("")
                password1.setText("")
            }
            else {
                val intent = Intent(this@Login, Profile::class.java)
                val bundle = Bundle()
                bundle.putString("email",email)
                bundle.putString("password",password)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}