package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommercefashion.databinding.ActivityCheckOutBinding
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

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.recyclerViewCheckOut.adapter = adapter

        adapter.add(CheckOutItem())
        adapter.add(CheckOutItem())
        adapter.add(CheckOutItem())
    }
}

class CheckOutItem : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.item_check_out
    }

}