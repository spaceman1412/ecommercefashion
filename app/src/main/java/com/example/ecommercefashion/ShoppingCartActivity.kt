package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.example.ecommercefashion.databinding.ActivityShoppingCartBinding
import com.example.ecommercefashion.databinding.ItemShoppingCartBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.*


class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingCartBinding
    private var price: Int = 0

    val adapter = GroupAdapter<GroupieViewHolder>()

    companion object {
        val TAG = "ShoppingCart"
        var listShopItem: MutableList<ShopItem> = mutableListOf()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.recyclerViewShoppingCart.adapter = adapter


//        GlobalScope.launch {
//            listenCartList()
//            addToAdapter()
//        }

        GlobalScope.launch {
            listenCartList()
            delay(5000L)
//            runOnUiThread {
//                addToAdapter()
//                println("Without delay")
//            }
        }


        val uid = FirebaseAuth.getInstance().uid
        Log.d(TAG, "The uid $uid")


        binding.checkOutButtonShoppingCart.setOnClickListener {
            val intent = Intent(this, CheckOutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addToAdapter() {

        Log.d(TAG, "Called add to Adapter")
        listShopItem.forEach {
            Log.d(TAG, "${it.shopItem.name} main scope")
            adapter.add(it)
        }
    }


    private fun listenCartList() {
        Log.e(ShoppingCartActivity.TAG, "Calling")

        GlobalScope.launch {
            getReference()
            println("Done")
        }

        Log.e(TAG, listShopItem.toString())
    }

    private suspend fun getReference() {
        val uid = FirebaseAuth.getInstance().uid


        val ref = FirebaseDatabase.getInstance().getReference("/cart/$uid")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listShopItem.clear()

                Log.e(ShoppingCartActivity.TAG, "Data changing")
                snapshot.children.forEach {
                    val shopItem = it.getValue(ItemCart::class.java)

                    //TODO: Create function to save item in current shopping cart to checkout database

                    if (shopItem != null) {
                        listShopItem.add(ShopItem(shopItem))

                        price += shopItem.price
                        Log.d(TAG, "Value is null")
                        Log.d(TAG, "Added to adapter ${shopItem.id}")
                    } else Log.e("ShoppingCart", "Item shop is null")
                }


                adapter.clear()
                addToAdapter()
                adapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Failed to adapter")
            }

        })


    }


    class ShopItem(val shopItem: ItemCart) :
        BindableItem<ItemShoppingCartBinding>() {
        override fun bind(viewBinding: ItemShoppingCartBinding, position: Int) {
            viewBinding.titleNameTextViewItemShoppingCart.text = shopItem.name
//            viewBinding.primaryImageViewItemShoppingCart.setImageResource(shopItem.primaryImage)
            viewBinding.deleteImageViewItemShoppingCart.setOnClickListener {
                Log.e("ShoppingCart", "Delete Clicked")
                val uid = FirebaseAuth.getInstance().uid
                FirebaseDatabase.getInstance().getReference("/cart/$uid/${shopItem.id}")
                    .removeValue()
            }
        }

        override fun getLayout(): Int {
            return R.layout.item_shopping_cart
        }

        override fun initializeViewBinding(view: View): ItemShoppingCartBinding {
            return ItemShoppingCartBinding.bind(view)
        }
    }
}



