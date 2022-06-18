package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecommercefashion.databinding.ActivityCouponBinding
import com.example.ecommercefashion.databinding.ActivityCoupons2Binding
import com.example.ecommercefashion.models.Coupon
import com.example.ecommercefashion.models.Notification
import com.google.firebase.database.FirebaseDatabase

class CouponsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCoupons2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoupons2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreate.setOnClickListener {
            val percentage = binding.txtDiscount.text.toString()
            val endDate = binding.txtEndDate.text.toString()
            val coupon = Coupon("a", percentage.toInt(),endDate)
            setUpCoupon(coupon)
        }
        binding.btnListOfCoupons.setOnClickListener {
            val intent = Intent(this,CouponActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setUpCoupon(coupon: Coupon) {
        val ref = FirebaseDatabase.getInstance().getReference("coupons").push()
        coupon.id = ref.key.toString()
        ref.setValue(coupon)
            .addOnCompleteListener {
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT)
            }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT)
            }
    }

}