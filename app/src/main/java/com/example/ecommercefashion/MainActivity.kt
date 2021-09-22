package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val click : LinearLayout = findViewById(R.id.linear_click)
        click.setOnClickListener{
            val intent = Intent(this,ShoppingCartActivity::class.java)
            startActivity(intent)
        }
    }
}