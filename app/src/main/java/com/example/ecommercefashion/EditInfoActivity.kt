package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ecommercefashion.databinding.ActivityEditInfoBinding
import com.example.ecommercefashion.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class EditInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditInfoBinding
    private val TAG : String = "EditInfoActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val uid = FirebaseAuth.getInstance().uid

        if (uid != null) {
            FirebaseDatabase.getInstance().getReference("users").child(uid).get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                binding.locationEditInfoEditText.setText(user?.location)
                binding.phoneNumerEditInfoEditText.setText(user?.phoneNumber)
                binding.nameEditInfoEditText.setText(user?.name)
            }.addOnFailureListener {
                Log.d(TAG, "onCreate: ${it}")
            }
        }

        binding.saveEditInfoButton.setOnClickListener {
            val name = binding.nameEditInfoEditText.text
            val phoneNumber = binding.phoneNumerEditInfoEditText.text
            val location = binding.locationEditInfoEditText.text

            if (uid != null) {
                FirebaseDatabase.getInstance().getReference("users").child(uid).get().addOnSuccessListener {
                    val user = it.getValue(User::class.java)
                    user?.name = name.toString()
                    user?.phoneNumber = phoneNumber.toString()
                    user?.location = location.toString()
                    FirebaseDatabase.getInstance().getReference("users").child(uid).setValue(user)
                        .addOnSuccessListener {
                            Log.d(TAG, "onCreate: OK")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "onCreate: ${it}")
                        }
                }
                finish()
            }
        }

    }



}