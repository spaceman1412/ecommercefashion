package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("MainActivity","The current user is ${currentUser?.uid.toString()}")
        if(currentUser == null) {
            val intent = Intent(this,LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        val click : LinearLayout = binding.linearClick
        click.setOnClickListener{
            val intent = Intent(this,ShoppingCartActivity::class.java)
            startActivity(intent)
        }

        val icon : ImageView = binding.searchIconMainActivity
        icon.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}