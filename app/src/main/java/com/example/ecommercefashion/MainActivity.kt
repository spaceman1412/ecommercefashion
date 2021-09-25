package com.example.ecommercefashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.example.ecommercefashion.databinding.ItemLargeMainactivityBinding
import com.example.ecommercefashion.databinding.ItemSmallMainActivityBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        val USER_KEY = "USER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("MainActivity", "The current user is ${currentUser?.uid.toString()}")
        if (currentUser == null) {
            val intent = Intent(this, LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        val icon: ImageView = binding.searchIconMainActivity
        icon.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.shoppingCartImageViewMainActivity.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }

        val recyclerView_large: RecyclerView = binding.recyclerViewLargeMainActivity

        val item_detail_list = listOf<ItemCart>(
            ItemCart(
                "1",
                "Cotton Pant",
                58,
                "man",
                R.drawable.pants,
                listOf(R.drawable.pants_list, R.drawable.hiphop_list),
                listOf("Trouser", "Winter")
            ),
            ItemCart(
                "2",
                "White Shirt",
                58,
                "man",
                R.drawable.whitetee,
                listOf(R.drawable.whiteshirt_listt, R.drawable.hiphop_list),
                listOf("Shirt")
            ),
        )


        val adapter_large = GroupAdapter<GroupieViewHolder>()
        recyclerView_large.adapter = adapter_large


        binding.chipGroupMainActivity.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { buttonView, isChecked ->
                adapter_large.clear()
                filterAdapterLarge()
            }
        }
        binding.searchIconMainActivity.setOnClickListener {
            val intent = Intent(this,SeachActivity::class.java)
            startActivity(intent)
        }



        adapter_large.add(ItemLarge(item_detail_list[0]))
        adapter_large.add(ItemLarge(item_detail_list[1]))
        adapter_large.setOnItemClickListener { item, view ->
            val intent = Intent(this, ItemDetail::class.java)
            val shopItem = item as ItemLarge
            intent.putExtra(USER_KEY, shopItem.item_detail)
            startActivity(intent)
        }

        val adapter_small = GroupAdapter<GroupieViewHolder>()
        binding.smallRecyclerViewMainActivity.adapter = adapter_small

        adapter_small.add(ItemSmall(item_detail_list[0]))
        adapter_small.add(ItemSmall(item_detail_list[1]))

        adapter_small.setOnItemClickListener { item, view ->
            val intent = Intent(this, ItemDetail::class.java)
            val shopItem = item as ItemSmall
            intent.putExtra(USER_KEY, shopItem.item_detail)
            startActivity(intent)
        }



    }

    private fun filterAdapterLarge() {
        val chips_group = binding.chipGroupMainActivity
        val ids = chips_group.checkedChipIds
        val item_detail_list = listOf<ItemCart>(
            ItemCart(
                "1",
                "Cotton Pant",
                58,
                "man",
                R.drawable.pants,
                listOf(R.drawable.pants_list, R.drawable.hiphop_list),
                listOf("Trousers", "Winter Collection")
            ),
            ItemCart(
                "2",
                "White Shirt",
                58,
                "man",
                R.drawable.whitetee,
                listOf(R.drawable.whiteshirt_listt, R.drawable.hiphop_list),
                listOf("Shirt")
            ),
        )

        val adapter_large = GroupAdapter<GroupieViewHolder>()
        binding.recyclerViewLargeMainActivity.adapter = adapter_large

        val titles = mutableListOf<String>()

        ids.forEach { id ->
            titles.add(chips_group.findViewById<Chip>(id).text.toString())
        }


        item_detail_list.forEach {
            var count = 0
            for (category in it.category) {
                for (title in titles) {
                    if (title == category) {
                        count++
                    }
                }
            }
            if(count == titles.size) adapter_large.add(ItemLarge(it))
        }

    }
}

class ItemLarge(val item_detail: ItemCart) : BindableItem<ItemLargeMainactivityBinding>() {
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

class ItemSmall(val item_detail: ItemCart) : BindableItem<ItemSmallMainActivityBinding>() {
    override fun bind(viewBinding: ItemSmallMainActivityBinding, position: Int) {
        viewBinding.priceTextViewSmallItem.text = "$${item_detail.price}"
        viewBinding.titleNameTextViewSmallItem.text = item_detail.name
        viewBinding.primaryImageImageViewSmallItem.setImageResource(item_detail.primaryImage)
    }

    override fun getLayout(): Int {
        return R.layout.item_small_main_activity
    }

    override fun initializeViewBinding(view: View): ItemSmallMainActivityBinding {
        return ItemSmallMainActivityBinding.bind(view)
    }
}

