package com.example.team10.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.team10.MainActivity
import com.example.team10.activity.boarding.BoardingOneActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this,
            MainActivity::class.java)
        startActivity(intent)
    }
}