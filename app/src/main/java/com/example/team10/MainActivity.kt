package com.example.team10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.team10.activity.boarding.BoardingOneActivity
import com.example.team10.activity.boarding.BoardingThreeActivity
import com.example.team10.activity.boarding.BoardingTwoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<BoardingOneActivity>(R.id.frag)
        }
    }
}