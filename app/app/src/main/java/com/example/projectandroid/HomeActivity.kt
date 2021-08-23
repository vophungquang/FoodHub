package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var tvUsername : TextView
    private lateinit var btnCart : ImageView
    private lateinit var imgShirt : ImageView
    private lateinit var imgPant : ImageView
    private lateinit var imgShoes : ImageView
    private lateinit var imgWatch : ImageView

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        findViewById()
        val username = intent.getStringExtra("user_name")
        val id = intent.getStringExtra("_id")
        val password = intent.getStringExtra("password")
        val email = intent.getStringExtra("email")
        tvUsername.text = username
        //
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.shirt ->{
                    val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                    intent.putExtra("type", "shirts")
                    intent.putExtra("user_name", username)
                    intent.putExtra("_id", id)
                    intent.putExtra("password", password)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                R.id.pant ->{
                    val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                    intent.putExtra("type", "pants")
                    intent.putExtra("user_name", username)
                    intent.putExtra("_id", id)
                    intent.putExtra("password", password)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                R.id.shoes ->{
                    val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                    intent.putExtra("type", "shoes")
                    intent.putExtra("user_name", username)
                    intent.putExtra("_id", id)
                    intent.putExtra("password", password)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                R.id.accessories ->{
                    val intent = Intent(this@HomeActivity, ProductActivity::class.java)
                    intent.putExtra("type", "accessories")
                    intent.putExtra("user_name", username)
                    intent.putExtra("_id", id)
                    intent.putExtra("password", password)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                R.id.profile -> {
                    val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                    intent.putExtra("user_name", username)
                    intent.putExtra("_id", id)
                    intent.putExtra("password", password)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
                R.id.setting -> {
                    Toast.makeText(this@HomeActivity, "Setting", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    val intent = Intent(this@HomeActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        //

        tvUsername.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            intent.putExtra("password", password)
            intent.putExtra("email", email)
            startActivity(intent)
        }

        btnCart.setOnClickListener{
            val intent = Intent(this@HomeActivity, CartActivity::class.java)
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            startActivity(intent)
        }
        imgShirt.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "shirts")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            intent.putExtra("password", password)
            intent.putExtra("email", email)
            startActivity(intent)
        }
        imgPant.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "pants")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            intent.putExtra("password", password)
            intent.putExtra("email", email)
            startActivity(intent)
        }
        imgShoes.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "shoes")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            intent.putExtra("password", password)
            intent.putExtra("email", email)
            startActivity(intent)
        }
        imgWatch.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "accessories")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            intent.putExtra("password", password)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }

    private fun findViewById(){
        tvUsername = findViewById(R.id.tv_username)
        btnCart = findViewById(R.id.img_cart)
        imgShirt = findViewById(R.id.img_shirt)
        imgPant = findViewById(R.id.img_pant)
        imgShoes = findViewById(R.id.img_shoes)
        imgWatch = findViewById(R.id.img_watch)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return  super.onOptionsItemSelected(item)
    }

}