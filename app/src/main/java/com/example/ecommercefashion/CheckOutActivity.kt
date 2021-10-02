package com.example.ecommercefashion

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercefashion.databinding.ActivityCheckOutBinding
import com.example.ecommercefashion.databinding.ItemCheckOutListBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import java.sql.Array

class CheckOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding
    companion object{
        val TAG = "CheckOutActivity"
    }

    val adapter = GroupAdapter<GroupieViewHolder>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityCheckOutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        listenCartDatabase()

        fetchCheckOutDatabase()

        fetchCheckOutList()
        val recyclerView : RecyclerView = binding.recyclerViewCheckOut
        recyclerView.adapter = adapter
//        adapter.add(CheckOutItemList())
//        adapter.add(CheckOutItemList())


//        val child : View = layoutInflater.inflate(R.layout.item_check_out,null)
//        child.findViewById<TextView>(R.id.titleName_textView_itemCheckOut).text = "Alo"
//        val child2 : View = layoutInflater.inflate(R.layout.item_check_out,null)
//        child2.findViewById<TextView>(R.id.titleName_textView_itemCheckOut).text = "Alo2"
//        binding.linearLayoutCheckOut.addView(child)
//        binding.linearLayoutCheckOut.addView(child2)


    }

    var listOfListItemCart  = HashMap<String,ArrayList<ItemCart?>>()

    private fun fetchCheckOutList(){
        //The function fetch not done yet so can't have data
        Log.d("CheckOutActivity","Called fetch function")

        listOfListItemCart.values.forEach {
            Log.d(TAG,it.toString())
            adapter.add(CheckOutItemList(layoutInflater,it))
//            val linearLayout : LinearLayout = binding.linearLayoutCheckOut
//            val child : View = layoutInflater.inflate(R.layout.item_check_out,null)
//            linearLayout.addView(child)
        }
    }

    private fun fetchCheckOutDatabase(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/check-out/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val listItem : ArrayList<ItemCart?> = ArrayList()
                    it.children.forEach {
                        val item : ItemCart? = it.getValue(ItemCart::class.java)
                        Log.d(TAG,item.toString())
                        listItem.add(item)
                    }
                    listOfListItemCart[it.key!!] = listItem
                }
                fetchCheckOutList()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("CheckOutActivity",error.message)
            }

        })
    }


    private fun listenCartDatabase(){
        val uid = FirebaseAuth.getInstance().uid

        val ref = FirebaseDatabase.getInstance().getReference("/cart/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val refGetKey = FirebaseDatabase.getInstance().getReference("/check-out/$uid").push()
                val key = refGetKey.key
                snapshot.children.forEach {
                    //TODO: Edit here save to list of itemcart not push to a node
                    val shopItem = it.getValue(ItemCart::class.java)
                    if (shopItem != null) {
                        saveToCheckOutDatabase(shopItem,key)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(ShoppingCartActivity.TAG, "Failed to adapter")
            }
        })
    }

    private fun saveToCheckOutDatabase(item: ItemCart,key: String?){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/check-out/$uid/$key/${item.id}")
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

class CheckOutItemList(layoutInflater: LayoutInflater,listItem : ArrayList<ItemCart?>) : BindableItem<ItemCheckOutListBinding>(){
    val layoutInflater : LayoutInflater = layoutInflater
    val listOfItemCart : ArrayList<ItemCart?> = listItem
    override fun bind(viewBinding: ItemCheckOutListBinding, position: Int) {
        listOfItemCart.forEach {
            val child : View = layoutInflater.inflate(R.layout.item_check_out,null)
            child.findViewById<TextView>(R.id.titleName_textView_itemCheckOut).text = it?.name
            child.findViewById<TextView>(R.id.price_textView_itemCheckOut).text = "$${it?.price}"
            child.findViewById<ImageView>(R.id.primaryImage_imageView_itemcheckOut).setImageResource(
                it!!.primaryImage)
            viewBinding.linearLayoutCheckOut.addView(child)
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_check_out_list
    }

    override fun initializeViewBinding(view: View): ItemCheckOutListBinding {
        return ItemCheckOutListBinding.bind(view)
    }
}