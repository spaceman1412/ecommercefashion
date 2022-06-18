package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommercefashion.databinding.ActivityCouponBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ActivityListProduct : AppCompatActivity() {
    private lateinit var binding: ActivityListProduct

    companion object{
        val adapter = GroupAdapter<GroupieViewHolder>()
        val TAG = "CouponActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)
    }
}