package com.example.trevokotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : Activity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Trevo Agro", Toast.LENGTH_SHORT).show()
        setContentView(R.layout.activity_main)
        val DrawerLayout= findViewById<DrawerLayout>(R.id.drawerLayout)
        findViewById<ImageView>(R.id.imageMenu).setOnClickListener(View.OnClickListener {
            DrawerLayout.openDrawer(GravityCompat.START)
        })
        val NavigationView = findViewById<NavigationView>(R.id.navigationView)
        NavigationView.itemIconTintList = null


    }
}