package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.ecommercefashion.databinding.ActivitySeachBinding
import com.example.ecommercefashion.databinding.ItemSearchBinding
import com.example.ecommercefashion.models.ItemCart
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class SeachActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeachBinding
    val item_detail_list  = mutableListOf<ItemCart>()



   lateinit var adapter : SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeachBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchProductList()


    }

    //filter the list with input
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val item : MenuItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView  =  item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
    private fun fetchProductList()
    {
        val ref = FirebaseDatabase.getInstance().getReference("/products")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val item_cart : ItemCart? = it.getValue(ItemCart::class.java)
                    if(item_cart != null)
                    {
                        item_detail_list.add(item_cart)
                    }
                }
                adapter = SearchAdapter(item_detail_list,this@SeachActivity)
                binding.recyclerViewSearchActivity.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("SearchActity",error.message.toString())
            }
        })
    }
}

