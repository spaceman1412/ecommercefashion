package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.example.ecommercefashion.databinding.ActivityShoppingCartBinding
import com.example.ecommercefashion.databinding.ItemShoppingCartBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingCartBinding

    companion object {
        val TAG = "ShoppingCart"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = GroupAdapter<GroupieViewHolder>()


        binding.recyclerViewShoppingCart.adapter = adapter
        val uid = FirebaseAuth.getInstance().uid
        Log.d(TAG,"The uid $uid")
//        adapter.add(ShopItem("AAAA"))
        val ref = FirebaseDatabase.getInstance().getReference("/cart/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                       val shopItem = it.getValue(ItemCart::class.java)

                       if (shopItem != null) {
                           adapter.add(ShopItem(shopItem))
                           Log.d(TAG, "Value is null")
                           Log.d(TAG, "Added to adapter ${shopItem.id}")
                       }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Failed to adapter")
            }
        })

    }
}

class ShopItem(val shopItem: ItemCart) : BindableItem<ItemShoppingCartBinding>() {
    override fun bind(viewBinding: ItemShoppingCartBinding, position: Int) {
        viewBinding.titleNameTextViewItemShoppingCart.text = shopItem.name
    }

    override fun getLayout(): Int {
        return R.layout.item_shopping_cart
    }

    override fun initializeViewBinding(view: View): ItemShoppingCartBinding {
        return ItemShoppingCartBinding.bind(view)
    }
}