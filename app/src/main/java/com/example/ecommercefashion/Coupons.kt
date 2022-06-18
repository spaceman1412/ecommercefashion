package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.ecommercefashion.databinding.ActivityCouponsBinding
import com.example.ecommercefashion.databinding.ActivityProductMgtBinding
import com.example.ecommercefashion.models.Coupon
import com.example.ecommercefashion.models.Coupons
import com.example.ecommercefashion.models.ProductDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Coupons : AppCompatActivity() {
    private lateinit var binding: ActivityCouponsBinding
    private lateinit var database: DatabaseReference

    companion object {
        val TAG = "CouponstActivity"
        private const val RC_SIGN_IN = 123

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCouponsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnCreateCoupons: Button = binding.btnCreate
        val btnOpenListCoupons: Button = binding.btnListOfCoupons
        btnOpenListCoupons.setOnClickListener {
            val intent = Intent(this, CouponActivity::class.java)
            startActivity(intent)
        }
        btnCreateCoupons.setOnClickListener {
            val uid = FirebaseAuth.getInstance().uid
            var ran = ((100000..999999).random()).toString()
            val id = "Item" + ran
            val ref = FirebaseDatabase.getInstance().getReference("/coupons/$id")
            var name = binding.txtCouponsName.text.toString()
            var discount = binding.txtDiscount.text.toString().toInt()
            var endDate = binding.txtEndDate.text.toString()

            ref.setValue(Coupons(ran, name, discount, endDate))
                .addOnCompleteListener {
                    Log.d(TAG,"Saved coupons to database")
                }
                .addOnFailureListener {
                    Log.d(RegisterFragment.TAG,it.message.toString())
                }
        }
    }

    private fun listenCoupon()
    {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/coupons")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CouponActivity.adapter.clear()
                snapshot.children.forEach {
                    val coupon = it.getValue(Coupon::class.java)
                    CouponActivity.adapter.add(
                        CouponActivity.ItemCoupon(
                            coupon!!,
                            this@Coupons
                        )
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}