package com.example.ecommercefashion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ecommercefashion.databinding.ActivityCouponBinding
import com.example.ecommercefashion.databinding.ItemCouponBinding
import com.example.ecommercefashion.databinding.ItemNotificationBinding
import com.example.ecommercefashion.models.Coupon
import com.example.ecommercefashion.models.Notification
import com.example.ecommercefashion.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class CouponActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCouponBinding

    companion object{
        val adapter = GroupAdapter<GroupieViewHolder>()
        val TAG = "CouponActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityCouponBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.couponCouponActivityRecyclerView
        val uid : String? = FirebaseAuth.getInstance().uid
        Log.d(TAG, "onCreate: ${uid}")
        //Check if with admin to listen to admin and with normal user to listen to normal user
        if(uid == null) listenCouponAdmin()
        else listenCouponUser()

        recyclerView.adapter = adapter


    }


    //Get all coupon list
    private fun listenCouponAdmin()
    {

        val ref = FirebaseDatabase.getInstance().getReference("coupons")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter.clear()
                snapshot.children.forEach {
                    val coupon = it.getValue(Coupon::class.java)
                    if(coupon != null) {
                        adapter.add(ItemCoupon(coupon, this@CouponActivity))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //Get coupon with this user
    private fun listenCouponUser()
    {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("users/${uid}/couponList")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter.clear()
                snapshot.children.forEach {
                    val coupon = it.getValue(Coupon::class.java)
                    if(coupon != null) {
                        adapter.add(ItemCoupon(coupon, this@CouponActivity))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    class ItemCoupon(val coupon: Coupon, val context: Context) :
        BindableItem<ItemCouponBinding>() {
        override fun bind(viewBinding: ItemCouponBinding, position: Int) {
            var color = ""
            when(coupon.percentage)
            {
                10 -> color = "#ff4da6"
                15 -> color = "#33cc33"
                20 -> color = "#ff1a1a"
            }
            val colorInt  = Color.parseColor(color)
            viewBinding.couponItemCouponCardView.setCardBackgroundColor(colorInt)
            viewBinding.percentItemCouponTextView.text = "-${coupon.percentage}%"
            viewBinding.dateItemCouponTextView.text = coupon.date
            viewBinding.root.setOnClickListener {
                val data  = Intent()
                data.putExtra("key",coupon)
                ((context) as Activity).setResult(RESULT_OK,data)
                ((context) as Activity).finish()
            }
        }

        override fun getLayout(): Int {
            return R.layout.item_coupon
        }

        override fun initializeViewBinding(view: View): ItemCouponBinding {
            return ItemCouponBinding.bind(view)
        }
    }

}