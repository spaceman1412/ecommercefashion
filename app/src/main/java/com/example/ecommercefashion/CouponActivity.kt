package com.example.ecommercefashion

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

        binding = ActivityCouponBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.couponCouponActivityRecyclerView
        listenCoupon()
        recyclerView.adapter = adapter
    }

    private fun listenCoupon()
    {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("users/${uid}/couponList")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter.clear()
                snapshot.children.forEach {
                    val coupon = it.getValue(Coupon::class.java)
                    adapter.add(ItemCoupon(coupon!!))
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    class ItemCoupon(val coupon: Coupon) :
        BindableItem<ItemCouponBinding>() {
        override fun bind(viewBinding: ItemCouponBinding, position: Int) {
            viewBinding.percentItemCouponTextView.text = "-${coupon.percentage}%"
            viewBinding.dateItemCouponTextView.text = coupon.date
        }

        override fun getLayout(): Int {
            return R.layout.item_coupon
        }

        override fun initializeViewBinding(view: View): ItemCouponBinding {
            return ItemCouponBinding.bind(view)
        }
    }

}