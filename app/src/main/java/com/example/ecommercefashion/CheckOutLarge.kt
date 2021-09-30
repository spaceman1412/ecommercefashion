package com.example.ecommercefashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommercefashion.databinding.ActivityCheckOutLargeBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class CheckOutLarge : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutLargeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutLargeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.recyclerViewCheckOutLarge.adapter = adapter

        adapter.add(CheckOutLargeItem())
        adapter.add(CheckOutLargeItem())
        adapter.add(CheckOutLargeItem())

    }


}
class CheckOutLargeItem : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_check_out
    }

}
