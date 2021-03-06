package com.example.ecommercefashion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.example.ecommercefashion.databinding.ItemLargeMainactivityBinding
import com.example.ecommercefashion.databinding.ItemSmallMainActivityBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        val TAG = "MainActivity"
        val USER_KEY = "USER_KEY"
        val adapter_large = GroupAdapter<GroupieViewHolder>()
        val adapter_small = GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val productList = mutableListOf<ItemCart>()

//        val item_cart1 = ItemCart("1","Cotton Pant",52,"Male","https://www.fashionkart.com.np/public/uploads/all/dE8uXgN0WQtxhD1CVn6yxCUgLK2dJ09ZCXpictGT.jpg", listOf(R.drawable.pants,R.drawable.pants_list), listOf("Trousers"))
//        val item_cart2 = ItemCart("1","Cotton Pant",52,"Male","", listOf(), listOf())
//        val item_cart3 = ItemCart("1","Cotton Pant",52,"Male","", listOf(), listOf())

//        productList.add(item_cart1)
//        productList.add(item_cart)
//        productList.add(item_cart)

//        val ref = FirebaseDatabase.getInstance().getReference("products").push()
//        item_cart1.id = ref.key
//        ref.setValue(item_cart1)
//            .addOnCompleteListener {
//                Log.d(TAG,"Saved value to database ${ref.key}")
//            }
//            .addOnFailureListener {
//                Log.d(TAG,it.message.toString())
//            }


        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("MainActivity", "The current user is ${currentUser?.uid.toString()}")

        if (currentUser == null) {
            val intent = Intent(this, LoginSreen::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.menuMainActivity.setOnClickListener {
            val intent = Intent(this,CheckOutActivity::class.java)
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

        binding.logoutImageViewMainActivity.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,LoginSreen::class.java)
            startActivity(intent)
        }


        val recyclerView_large: RecyclerView = binding.recyclerViewLargeMainActivity



        fetchProductList()

        recyclerView_large.adapter = adapter_large

        binding.chipGroupMainActivity.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { buttonView, isChecked ->
                adapter_large.clear()
                filterAdapterLarge()
            }
        }

        binding.searchIconMainActivity.setOnClickListener {
            val intent = Intent(this, SeachActivity::class.java)
            startActivity(intent)
        }
        binding.userIconMainActivity.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        adapter_large.setOnItemClickListener { item, view ->
            val intent = Intent(this, ItemDetail::class.java)
            val shopItem = item as ItemLarge
            intent.putExtra(USER_KEY, shopItem.item_detail)
            startActivity(intent)
        }

        binding.smallRecyclerViewMainActivity.adapter = adapter_small

        adapter_small.setOnItemClickListener { item, view ->
            val intent = Intent(this, ItemDetail::class.java)
            val shopItem = item as ItemSmall
            intent.putExtra(USER_KEY, shopItem.item_detail)
            startActivity(intent)
        }
    }



    private fun setUpProductDatabase( productList: MutableList<ItemCart>)
    {

    }

    private fun fetchProductList()
    {
        Log.d(TAG, "fetchProductList: ")
        val ref = FirebaseDatabase.getInstance().getReference("/products")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter_large.clear()
                adapter_small.clear()
                snapshot.children.forEach {
                    val item_cart : ItemCart? = it.getValue(ItemCart::class.java)
                    Log.d(TAG, "onDataChange: ${it.toString()}")
                    if(item_cart != null)
                    {
                        adapter_large.add(ItemLarge(item_cart))
                        adapter_small.add(ItemSmall(item_cart))
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity",error.message.toString())
            }
        })
    }


    private fun filterAdapterLarge() {
        val chips_group = binding.chipGroupMainActivity
        val ids = chips_group.checkedChipIds
        val titles = mutableListOf<String>()
        val adapter_large = GroupAdapter<GroupieViewHolder>()

        ids.forEach { id ->
            titles.add(chips_group.findViewById<Chip>(id).text.toString())
        }

        val ref = FirebaseDatabase.getInstance().getReference("/products")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val item_detail_list = mutableListOf<ItemCart>()
                snapshot.children.forEach {
                    val item_cart : ItemCart? = it.getValue(ItemCart::class.java)
                    if(item_cart != null)
                    {
                        item_detail_list.add(item_cart)
                    }
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
            override fun onCancelled(error: DatabaseError) {
                Log.d("MainActivity",error.message.toString())
            }
        })
        adapter_large.setOnItemClickListener { item, view ->
            val intent = Intent(this, ItemDetail::class.java)
            val shopItem = item as ItemLarge
            intent.putExtra(USER_KEY, shopItem.item_detail)
            startActivity(intent)
        }
        binding.recyclerViewLargeMainActivity.adapter = adapter_large
    }
}

class ItemLarge(val item_detail: ItemCart) : BindableItem<ItemLargeMainactivityBinding>() {
    override fun bind(viewBinding: ItemLargeMainactivityBinding, position: Int) {
        viewBinding.titleNameTextViewLargeItem.text = item_detail.name
        viewBinding.priceTextViewLargeItem.text = "$${item_detail.price}"
        val imageView = viewBinding.primaryImageImageViewLargeItem
        Glide.with(viewBinding.root.context).load(item_detail.primaryImageUrl).into(imageView)
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
        val imageView = viewBinding.primaryImageImageViewSmallItem
        Glide.with(viewBinding.root.context).load(item_detail.primaryImageUrl).into(imageView)
//        viewBinding.primaryImageImageViewSmallItem.setImageResource(item_detail.primaryImage)
    }

    override fun getLayout(): Int {
        return R.layout.item_small_main_activity
    }

    override fun initializeViewBinding(view: View): ItemSmallMainActivityBinding {
        return ItemSmallMainActivityBinding.bind(view)
    }
}

