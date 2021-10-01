package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ecommercefashion.databinding.ActivityCheckOutBinding
import com.example.ecommercefashion.databinding.ItemCheckOutBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class CheckOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val uid = FirebaseAuth.getInstance().uid

        val ref = FirebaseDatabase.getInstance().getReference("/cart/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val refGetKey = FirebaseDatabase.getInstance().getReference("/check-out/$uid").push()
                val key = refGetKey.key
                snapshot.children.forEach {
                    val shopItem = it.getValue(ItemCart::class.java)
                    if (shopItem != null) {
                        saveToDatabase(shopItem,key)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(ShoppingCartActivity.TAG, "Failed to adapter")
            }
        })
    }

    private fun saveToDatabase(item: ItemCart,key: String?){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/check-out/$uid/$key").push()
        ref.setValue(item)
            .addOnCompleteListener {
                Log.d("CheckOutActivity","Complete save to database ${ref.key}")
            }
            .addOnFailureListener {
                Log.d("CheckOutActivity",it.message.toString())
            }
        Log.d("CheckOutActivity", "Added to adapter ${item.id}")
    }
}

//class CheckOutItem(val item: ItemCart) : BindableItem<ItemCheckOutBinding>(){
//    override fun bind(viewBinding: ItemCheckOutBinding, position: Int) {
//        viewBinding.titleNameTextViewItemCheckOut.text = item.name
//        viewBinding.primaryImageImageViewCheckOut.setImageResource(item.primaryImage)
//        viewBinding.priceTextViewItemCheckOut.text = "$${item.price}"
//    }
//
//    override fun getLayout(): Int {
//        return R.layout.item_check_out
//    }
//
//    override fun initializeViewBinding(view: View): ItemCheckOutBinding {
//        return ItemCheckOutBinding.bind(view)
//    }
//}