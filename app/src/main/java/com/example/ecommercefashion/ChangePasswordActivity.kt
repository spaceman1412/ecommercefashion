package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecommercefashion.databinding.ActivityChangePasswordBinding
import com.example.ecommercefashion.models.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val uid = FirebaseAuth.getInstance().uid
        if (uid != null) {
            FirebaseDatabase.getInstance().getReference("users").child(uid).get()
                .addOnSuccessListener {
                    val user = it.getValue(User::class.java)

                    binding.saveChangePasswordActivityButton.setOnClickListener {
                        val userauth = Firebase.auth.currentUser
                        val credential = EmailAuthProvider
                            .getCredential(user!!.email, binding.currentPassChangePasswordActivityEditText.text.toString())

                        if (userauth != null) {
                            userauth.reauthenticate(credential)
                                .addOnCompleteListener { Log.d("ChangePasswordActivity", "User re-authenticated.") }
                                .addOnFailureListener{ Log.d("ChangePasswordActivity", "onCreate: ${it}")}
                        }

                        val newPassword = binding.newPassChangePasswordActivityEditText.text.toString()
                        userauth!!.updatePassword(newPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this,"User password updated.",Toast.LENGTH_SHORT)
                                    Log.d("ChangePassword", "User password updated.")
                                    finish()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT)
                            }

                }

        }


        }
    }

}