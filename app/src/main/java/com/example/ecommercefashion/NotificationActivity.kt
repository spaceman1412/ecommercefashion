package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ecommercefashion.databinding.ActivityNotificationBinding
import com.example.ecommercefashion.databinding.ItemLargeMainactivityBinding
import com.example.ecommercefashion.databinding.ItemNotificationBinding
import com.example.ecommercefashion.models.Coupon
import com.example.ecommercefashion.models.ItemCart
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
import java.time.LocalDate

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    companion object {
        val adapter = GroupAdapter<GroupieViewHolder>()
        val TAG = "NotificationActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerViewNotification

        val coupon: Coupon = Coupon("1", 20, "29/09/1988")
        val notification: Notification = Notification("1", "aaa", "aaa", coupon, "29/09/1988", "")

        listenNotification()
        recyclerView.adapter = adapter
    }

    private fun listenNotification()
    {
        val ref = FirebaseDatabase.getInstance().getReference("notifications")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter.clear()
                snapshot.children.forEach {
                    val notification = it.getValue(Notification::class.java)
                    adapter.add(ItemNotification(notification!!))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG,error.toString())
            }

        })
    }

    private fun setUpNotification(notification: Notification) {
        val ref = FirebaseDatabase.getInstance().getReference("notifications").push()
        notification.id = ref.key.toString()
        ref.setValue(notification)
            .addOnCompleteListener {
                Log.d(TAG, "Saved value to database ${ref.key}")
            }
            .addOnFailureListener {
                Log.d(TAG, it.message.toString())
            }
    }

    private fun addCouponToUser(coupon: Coupon)
    {
        val uid = FirebaseAuth.getInstance().uid
        if (uid != null) {
            FirebaseDatabase.getInstance().getReference("users").child(uid).child("coupon").setValue(coupon)
                .addOnSuccessListener {
                    Log.d(TAG, "addCouponToUser: ${it}")
                }
                .addOnFailureListener{
                    Log.d(TAG, "addCouponToUser: ${it}")
                }
        }
    }


    class ItemNotification(val notification: Notification) :
        BindableItem<ItemNotificationBinding>() {
        override fun bind(viewBinding: ItemNotificationBinding, position: Int) {
            viewBinding.titleNotificationItemTextView.setText(notification.title)
            viewBinding.descriptionNotificationItemTextView.setText(notification.description)
            viewBinding.dateTimeNotificationItemTextView.setText(notification.date)
            viewBinding.coupounNotificationItemButton.setOnClickListener {
                val uid = FirebaseAuth.getInstance().uid
                if (uid != null) {
                    val ref = FirebaseDatabase.getInstance().getReference("users/${uid}")
                    ref.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val user = snapshot.getValue(User::class.java)
                            val couponList = user?.couponList
                            couponList?.add(notification.coupon!!)
                            FirebaseDatabase.getInstance().getReference("users").child(uid).child("couponList").setValue(couponList)
                            Log.d(TAG, "onDataChange: ${user}")
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
        }

        override fun getLayout(): Int {
            return R.layout.item_notification
        }

        override fun initializeViewBinding(view: View): ItemNotificationBinding {
            return ItemNotificationBinding.bind(view)
        }
    }
}