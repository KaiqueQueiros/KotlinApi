package com.example.trevokotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.trevokotlin.ui.orcamento.CartActivity
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Trevo Agro", Toast.LENGTH_SHORT).show()
        val DrawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        findViewById<ImageView>(R.id.imageMenu).setOnClickListener(View.OnClickListener {
            DrawerLayout.openDrawer(GravityCompat.START)
        })

        val navigationView: NavigationView = findViewById(R.id.navigationView);
        navigationView.itemIconTintList = null;

        val navController: NavController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(navigationView, navController)

        val imageBag = findViewById<ImageView>(R.id.imageBag)
        imageBag.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

    }



}