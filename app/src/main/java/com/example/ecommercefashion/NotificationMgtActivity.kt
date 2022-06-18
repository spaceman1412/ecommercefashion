package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecommercefashion.databinding.ActivityNotificationMgtBinding
import com.example.ecommercefashion.models.Coupon
import com.example.ecommercefashion.models.Notification
import com.google.firebase.database.FirebaseDatabase

class NotificationMgtActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationMgtBinding
    private var coupon : Coupon? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationMgtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pickCouponNotificationMgtTextView.setOnClickListener {
            val intent = Intent(this,CouponActivity::class.java)

            startActivityForResult(intent, ShoppingCartActivity.REQUEST_CODE_COUPON)
        }

        binding.btnCreate.setOnClickListener {
            val name = binding.txtNotificationName.text.toString()
            val endDate = binding.txtEndDate.text.toString()
            val imageUrl = binding.txtImageUrl.text.toString()
            val description = binding.txtDescription.text.toString()

            val notification = Notification("",name,description,coupon,endDate,imageUrl)
            setUpNotification(notification)
        }
        binding.btnListOfNotifications.setOnClickListener {
            val intent = Intent(this,NotificationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setUpNotification(notification: Notification) {
        val ref = FirebaseDatabase.getInstance().getReference("notifications").push()
        notification.id = ref.key.toString()
        ref.setValue(notification)
            .addOnCompleteListener {
                Toast.makeText(this,"Success", Toast.LENGTH_SHORT)
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT)
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ShoppingCartActivity.REQUEST_CODE_COUPON)
        {
            if(resultCode == RESULT_OK)
            {
                coupon = data?.getParcelableExtra<Coupon>("key")
            }
        }
    }
}