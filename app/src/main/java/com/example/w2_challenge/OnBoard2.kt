package com.example.w2_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class OnBoard2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding2)

        val btnNext2 = findViewById<ImageButton>(R.id.imbtn2)
        btnNext2.setOnClickListener {
            val intent = Intent(this,OnBoard3::class.java)
            startActivity(intent)
        }
    }
}