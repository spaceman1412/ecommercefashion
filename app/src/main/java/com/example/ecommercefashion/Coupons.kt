package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.ecommercefashion.databinding.ActivityCouponsBinding
import com.example.ecommercefashion.databinding.ActivityProductMgtBinding
import com.example.ecommercefashion.models.Coupons
import com.example.ecommercefashion.models.ProductDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
        val btnCreateProduct: Button = binding.btnCreate
        btnCreateProduct.setOnClickListener {
            val uid = FirebaseAuth.getInstance().uid
            val ref = FirebaseDatabase.getInstance().getReference("/coupons/$uid")
            var name = binding.txtCouponsName.text.toString()
            var discount = binding.txtDiscount.text.toString().toInt()
            var endDate = binding.txtEndDate.text.toString()

            ref.setValue(Coupons(uid.toString(), name, discount, endDate))
                .addOnCompleteListener {
                    Log.d(TAG,"Saved coupons to database")
                }
                .addOnFailureListener {
                    Log.d(RegisterFragment.TAG,it.message.toString())
                }
        }
    }
}