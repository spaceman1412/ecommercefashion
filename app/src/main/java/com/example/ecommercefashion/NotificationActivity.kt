package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ecommercefashion.databinding.ActivityNotificationBinding
import com.example.ecommercefashion.databinding.ItemLargeMainactivityBinding
import com.example.ecommercefashion.databinding.ItemNotificationBinding
import com.example.ecommercefashion.models.Coupon
import com.example.ecommercefashion.models.ItemCart
import com.example.ecommercefashion.models.Notification
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import java.time.LocalDate

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    companion object {
        val adapter = GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerViewNotification

        val coupon : Coupon = Coupon("1",20, "29/09/1988")
        val notification: Notification = Notification("1","aaa","aaa",coupon,"29/09/1988","")

        adapter.add(ItemNotification(notification))
        adapter.add(ItemNotification(notification))
        adapter.add(ItemNotification(notification))

        recyclerView.adapter = adapter

    }

    class ItemNotification(val notification: Notification) : BindableItem<ItemNotificationBinding>() {
        override fun bind(viewBinding: ItemNotificationBinding, position: Int) {
            viewBinding.titleNotificationItemTextView.setText(notification.title)
            viewBinding.descriptionNotificationItemTextView.setText(notification.description)
            viewBinding.dateTimeNotificationItemTextView.setText(notification.date)
        }

        override fun getLayout(): Int {
            return R.layout.item_notification
        }

        override fun initializeViewBinding(view: View): ItemNotificationBinding {
            return ItemNotificationBinding.bind(view)
        }
    }
}