package com.example.w2_challenge

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
        val SignBtn = findViewById<Button>(R.id.signinbtn)
        SignBtn.setOnClickListener {
            val intent = Intent(this,OnBoard1::class.java)
            startActivity(intent)
        }
    }
}