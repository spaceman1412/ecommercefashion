package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.example.ecommercefashion.databinding.ItemLargeMainactivityBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.viewbinding.BindableItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("MainActivity","The current user is ${currentUser?.uid.toString()}")
        if(currentUser == null) {
            val intent = Intent(this,LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


//        val click : LinearLayout = binding.linearClick
//        click.setOnClickListener{
//            val intent = Intent(this,ItemDetail::class.java)
//            startActivity(intent)
//        }

        val icon : ImageView = binding.searchIconMainActivity
        icon.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val recyclerView_large : RecyclerView = binding.recyclerViewLargeMainActivity

        val item_detail_list = listOf<ItemCart>(
            ItemCart(1,"Cotton Pant",58,"man",R.drawable.pants,listOf(R.drawable.pants_list,R.drawable.hiphop_list)),
            ItemCart(2,"White Shirt",58,"man",R.drawable.whitetee,listOf(R.drawable.whiteshirt_listt,R.drawable.hiphop_list)),
        )

        val adapter = GroupAdapter<GroupieViewHolder>()
        recyclerView_large.adapter = adapter
        adapter.add(ItemLarge(item_detail_list[0]))
        adapter.add(ItemLarge(item_detail_list[1]))



    }

    class ItemLarge(val item_detail: ItemCart) : BindableItem<ItemLargeMainactivityBinding>(){
        override fun bind(viewBinding: ItemLargeMainactivityBinding, position: Int) {
            viewBinding.titleNameTextViewLargeItem.text = item_detail.name
            viewBinding.priceTextViewLargeItem.text = "$${item_detail.price}"
            viewBinding.primaryImageImageViewLargeItem.setImageResource(item_detail.primaryImage)
            viewBinding.sexTextViewLargeItem.text = "For ${item_detail.sex}"
        }

        override fun getLayout(): Int {
            return R.layout.item_large_mainactivity
        }

        override fun initializeViewBinding(view: View): ItemLargeMainactivityBinding {
            return ItemLargeMainactivityBinding.bind(view)
        }
    }
}