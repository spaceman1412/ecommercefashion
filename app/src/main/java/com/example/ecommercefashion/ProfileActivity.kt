package com.example.ecommercefashion

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.example.ecommercefashion.databinding.ActivityProfileBinding
import com.example.ecommercefashion.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.notificationProfileCardView.setOnClickListener {
            val intent = Intent(this,NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.editInformationProfileCardView.setOnClickListener {
            Log.d("ProfileActivity","Clicked")
            val intent = Intent(this,EditInfoActivity::class.java)
            startActivity(intent)
        }

        binding.logOutProfileActivitCardView.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.changePasswordProfileActivityCardView.setOnClickListener {
            val intent = Intent(this,ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        val uid = FirebaseAuth.getInstance().uid
        if (uid != null) {
            FirebaseDatabase.getInstance().getReference("users").child(uid).get()
                .addOnSuccessListener {
                    val user = it.getValue(User::class.java)
                    binding.usernameProfileActivityTextView.setText(user?.name)
                    binding.locationProfileActivityTextView.setText(user?.location)
                }
                .addOnFailureListener{
                    Log.d("ProfileActivity", "onCreate: ${it}")
                }
        }

        binding.avatarProfileActivityImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            //proceed check what image selected was
            Log.d("RegisterActivity", "Photo selected")

            selectedUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedUri)

            val select_photo = binding.avatarProfileActivityImageView

            select_photo.setImageBitmap(bitmap)

        }
    }


}