package com.example.team10.activity.boarding

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.team10.R


class BoardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_onboarding_two)

        val btnNext = findViewById<ImageButton>(R.id.btn_next)
        btnNext.setOnClickListener {
            val intent = Intent(this,
                BoardingThreeActivity::class.java)
            startActivity(intent)
        }
    }

}