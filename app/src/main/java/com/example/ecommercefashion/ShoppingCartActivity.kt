package com.example.ecommercefashion

import android.content.Intent
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
    private var price : Int = 0
    companion object {
        val TAG = "ShoppingCart"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val adapter = GroupAdapter<GroupieViewHolder>()


        binding.recyclerViewShoppingCart.adapter = adapter
        val uid = FirebaseAuth.getInstance().uid
        Log.d(TAG, "The uid $uid")
//        adapter.add(ShopItem("AAAA"))
        val ref = FirebaseDatabase.getInstance().getReference("/cart/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val shopItem = it.getValue(ItemCart::class.java)

                    if (shopItem != null) {
                        adapter.add(ShopItem(shopItem))
                        price += shopItem.price
                        Log.d(TAG, "Value is null")
                        Log.d(TAG, "Added to adapter ${shopItem.id}")
                    }
                }
                binding.priceTextViewActivityShoppingCart.text = "$$price"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Failed to adapter")
            }
        })

        binding.checkOutButtonShoppingCart.setOnClickListener {
            val intent = Intent(this,CheckOutActivity::class.java)
            startActivity(intent)
        }

    }
}

class ShopItem(val shopItem: ItemCart) : BindableItem<ItemShoppingCartBinding>() {
    override fun bind(viewBinding: ItemShoppingCartBinding, position: Int) {
        viewBinding.titleNameTextViewItemShoppingCart.text = shopItem.name
        viewBinding.primaryImageViewItemShoppingCart.setImageResource(shopItem.primaryImage)
    }

    override fun getLayout(): Int {
        return R.layout.item_shopping_cart
    }

    override fun initializeViewBinding(view: View): ItemShoppingCartBinding {
        return ItemShoppingCartBinding.bind(view)
    }
}