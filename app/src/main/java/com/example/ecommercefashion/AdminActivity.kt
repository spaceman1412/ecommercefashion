package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ecommercefashion.databinding.ActivityAdminBinding
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    companion object {
        val TAG = "AdminActivity"
        private const val RC_SIGN_IN = 100

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, LoginSreen::class.java)
            startActivity(intent)
        }

        binding.btnCoupons.setOnClickListener {

            val intent = Intent(this,CouponsActivity::class.java)
            startActivity(intent)
        }
        binding.btnNotification.setOnClickListener {
            val intent = Intent(this,NotificationMgtActivity::class.java)
            startActivity(intent)
        }



    }
}