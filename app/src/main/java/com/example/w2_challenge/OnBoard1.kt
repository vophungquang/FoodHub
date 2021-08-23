package com.example.w2_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class OnBoard1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding1)
        val btnNext1 = findViewById<ImageButton>(R.id.imbtn1)
        btnNext1.setOnClickListener {
            val intent = Intent(this,OnBoard2::class.java)
            startActivity(intent)
        }
    }
}