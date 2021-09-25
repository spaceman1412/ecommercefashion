package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.ecommercefashion.databinding.ActivitySeachBinding
import com.example.ecommercefashion.databinding.ItemSearchBinding
import com.example.ecommercefashion.models.ItemCart
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class SeachActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeachBinding
    val item_detail_list  = mutableListOf<ItemCart>(
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
    val adapter = SearchAdapter(item_detail_list)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeachBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerViewSearchActivity.adapter = adapter


    }

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
}

